package com.example.tonwallet.components.WIP

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.ton.api.liteclient.config.LiteClientConfigGlobal
import org.ton.api.pk.PrivateKeyEd25519
import org.ton.bitstring.BitString
import org.ton.block.AccountInfo
import org.ton.block.AddrStd
import org.ton.block.MsgAddressInt
import org.ton.block.StateInit
import org.ton.block.VarUInteger
import org.ton.boc.BagOfCells
import org.ton.cell.Cell
import org.ton.cell.CellBuilder
import org.ton.cell.buildCell
import org.ton.contract.wallet.WalletContract
import org.ton.crypto.Ed25519
import org.ton.crypto.encodeHex
import org.ton.crypto.encoding.base64
import org.ton.crypto.hex
import org.ton.hashmap.HashMapE
import org.ton.lite.client.LiteClient
import org.ton.lite.client.internal.FullAccountState
import org.ton.mnemonic.Mnemonic
import org.ton.tl.ByteString.Companion.toByteString
import org.ton.tlb.constructor.tlbCodec
import org.ton.tlb.storeTlb
import java.math.BigInteger
import java.net.URL
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.properties.Delegates


private const val TAG = "TonViewModel"

//class TonViewModelMocked : TonViewModel() {
//    init {
//        address = BitString("0:00")
//        balance = 0
//        isLoading = false
//    }
//}

open class TonViewModel(private val isPreview: Boolean = false) : ViewModel() {

    var passcode: String = ""
    var passcodeLength: Int = 4

    var wordCount = 24
    var mnemonic: List<String> = List(wordCount) { "" }
    lateinit var seed: ByteArray
    lateinit var privateKey: ByteArray
    lateinit var publicKey: ByteArray
    lateinit var sharedKey: ByteArray

    private lateinit var networkConfig: String
    private lateinit var liteClient: LiteClient

    var workchainId: Int = 0
    lateinit var address: BitString
    fun addressFormatted(): String {
        if (isPreview) return "UQBF…AoKP"
        val addrString = AddrStd(0, address).toString()
        return addrString.substring(0, 4) + "…" + addrString.substring(addrString.length - 4)
    }

    var balance by Delegates.notNull<Long>()
    fun balanceInteger(): String {
        return (balance / 1_000_000_000).toString()
    }

    fun balanceFractional(): String {
        val nanotons = balance % 1_000_000_000
        return nanotons.toString().substring(0, 4)
    }

    protected var isLoading = true

    fun isReady(): Boolean {
        val isReady = ::address.isInitialized && balance != null && !isLoading
        Log.v(TAG, "isReady: ${isReady}")
        return isReady
    }

    // org/ton/tonkotlinusecase/contracts/wallet/WalletV4R2.kt:35
    lateinit var CODE_V4R2: Cell

    // org/ton/tonkotlinusecase/contracts/wallet/HighloadWallet.kt:35
    lateinit var CODE_HILO: Cell

    // ton-kotlin-contract-jvm-0.3.0-SNAPSHOT-sources.jar!/commonMain/org/ton/contract/wallet/WalletV3Contract.kt:88
    val CODE_V3R2: Cell =
        Cell("FF0020DD2082014C97BA218201339CBAB19F71B0ED44D0D31FD31F31D70BFFE304E0A4F2608308D71820D31FD31FD31FF82313BBF263ED44D0D31FD31FD3FFD15132BAF2A15144BAF2A204F901541055F910F2A3F8009320D74A96D307D402FB00E8D101A4C8CB1FCB1FCBFFC9ED54")

    init {
        if (this.isPreview) {
            Log.v(TAG, "init preview")
            address = BitString("01010101")
            balance = 56_232_200_000
            isLoading = false
        } else {
            Log.v(TAG, "init")

            CODE_V4R2 = BagOfCells(
                base64("te6cckECFAEAAtQAART/APSkE/S88sgLAQIBIAIDAgFIBAUE+PKDCNcYINMf0x/THwL4I7vyZO1E0NMf0x/T//QE0VFDuvKhUVG68qIF+QFUEGT5EPKj+AAkpMjLH1JAyx9SMMv/UhD0AMntVPgPAdMHIcAAn2xRkyDXSpbTB9QC+wDoMOAhwAHjACHAAuMAAcADkTDjDQOkyMsfEssfy/8QERITAubQAdDTAyFxsJJfBOAi10nBIJJfBOAC0x8hghBwbHVnvSKCEGRzdHK9sJJfBeAD+kAwIPpEAcjKB8v/ydDtRNCBAUDXIfQEMFyBAQj0Cm+hMbOSXwfgBdM/yCWCEHBsdWe6kjgw4w0DghBkc3RyupJfBuMNBgcCASAICQB4AfoA9AQw+CdvIjBQCqEhvvLgUIIQcGx1Z4MesXCAGFAEywUmzxZY+gIZ9ADLaRfLH1Jgyz8gyYBA+wAGAIpQBIEBCPRZMO1E0IEBQNcgyAHPFvQAye1UAXKwjiOCEGRzdHKDHrFwgBhQBcsFUAPPFiP6AhPLassfyz/JgED7AJJfA+ICASAKCwBZvSQrb2omhAgKBrkPoCGEcNQICEekk30pkQzmkD6f+YN4EoAbeBAUiYcVnzGEAgFYDA0AEbjJftRNDXCx+AA9sp37UTQgQFA1yH0BDACyMoHy//J0AGBAQj0Cm+hMYAIBIA4PABmtznaiaEAga5Drhf/AABmvHfaiaEAQa5DrhY/AAG7SB/oA1NQi+QAFyMoHFcv/ydB3dIAYyMsFywIizxZQBfoCFMtrEszMyXP7AMhAFIEBCPRR8qcCAHCBAQjXGPoA0z/IVCBHgQEI9FHyp4IQbm90ZXB0gBjIywXLAlAGzxZQBPoCFMtqEssfyz/Jc/sAAgBsgQEI1xj6ANM/MFIkgQEI9Fnyp4IQZHN0cnB0gBjIywXLAlAFzxZQA/oCE8tqyx8Syz/Jc/sAAAr0AMntVGliJeU=")
            ).first()
            CODE_HILO = BagOfCells(
                hex("B5EE9C724101090100E5000114FF00F4A413F4BCF2C80B010201200203020148040501EAF28308D71820D31FD33FF823AA1F5320B9F263ED44D0D31FD33FD3FFF404D153608040F40E6FA131F2605173BAF2A207F901541087F910F2A302F404D1F8007F8E16218010F4786FA5209802D307D43001FB009132E201B3E65B8325A1C840348040F4438AE63101C8CB1F13CB3FCBFFF400C9ED54080004D03002012006070017BD9CE76A26869AF98EB85FFC0041BE5F976A268698F98E99FE9FF98FA0268A91040207A0737D098C92DBFC95DD1F140034208040F4966FA56C122094305303B9DE2093333601926C21E2B39F9E545A")
            ).first()

            val job = viewModelScope.launch(context = Dispatchers.IO) {
                Log.v(TAG, "viewModelScope job started")

                val serverVersion = withContext(Dispatchers.IO) {
                    networkConfig = getTonGlobalConfig()

                    val json = Json { ignoreUnknownKeys = true }
                    val config = json.decodeFromString<LiteClientConfigGlobal>(networkConfig)
                    liteClient = LiteClient(viewModelScope.coroutineContext, config)
                    liteClient.getServerVersion()
                }
                Log.v(TAG, "liteClient ServerVersion: ${serverVersion}")

            }
            Log.v(TAG, "initialized.")
        }
    }

    private fun getTonGlobalConfig(): String {
        // TODO: caching
        val configText: String =
            URL("https://ton-blockchain.github.io/global.config.json").readText()
        Log.v(TAG, "configText.length: ${configText.length}")

        return configText
    }


    private val userLiveData: MutableLiveData<WalletUiState> = MutableLiveData<WalletUiState>()

    private val _uiState = MutableStateFlow(WalletUiState())
    val uiState: StateFlow<WalletUiState> = _uiState.asStateFlow()

    private val scope = CoroutineScope(Job() + Dispatchers.Main) //  + exceptionHandler ???
    val scopeFailureProtected = CoroutineScope(SupervisorJob())

    fun generateSeed() {
        Log.v(TAG, "::::: CODE_V4R2: ${CODE_V4R2.bits.toByteArray().encodeHex()}")
        Log.v(TAG, "::::: CODE_HILO: ${CODE_HILO.bits.toByteArray().encodeHex()}")
        Log.v(TAG, "}}}}} CODE_V4R2: ${CODE_V4R2.toString()}")
        Log.v(TAG, "}}}}} CODE_HILO: ${CODE_HILO.toString()}")

        runBlocking {
            mnemonic = Mnemonic.generate()
        }
//        val job = viewModelScope.launch {
//            mnemonic = Mnemonic.generate()
//        }
    }

    fun isSeedValid(seedPhrase: List<String>): Boolean {
        if (Mnemonic.isValid(seedPhrase)) {
            mnemonic = seedPhrase

            val isPasswordNeeded = Mnemonic.isPasswordNeeded(seedPhrase)
            Log.v(TAG, "=== isPasswordNeeded: $isPasswordNeeded")

            seed = Mnemonic.toSeed(seedPhrase)
            privateKey = PrivateKeyEd25519(seed).key.toByteArray()
            publicKey = Ed25519.publicKey(privateKey)
            sharedKey = Ed25519.sharedKey(privateKey, publicKey)

            Log.v(TAG, "=== seed.size: ${seed.size}")
            Log.v(TAG, "=== seed.toByteString().encodeHex(): ${seed.toByteString().encodeHex()}")

            Log.v(TAG, "=== privateKey.size: ${privateKey.size}")
            Log.v(TAG, "=== privateKey: ${privateKey.toString()}")
            Log.v(
                TAG,
                "=== privateKey.toByteString().decodeToString(): ${
                    privateKey.toByteString().decodeToString()
                }"
            )
            Log.v(
                TAG,
                "=== privateKey.toByteString().encodeBase64(): ${
                    privateKey.toByteString().encodeBase64()
                }"
            )
            Log.v(
                TAG,
                "=== privateKey.toByteString().encodeHex(): ${
                    privateKey.toByteString().encodeHex()
                }"
            )

            Log.v(TAG, "=== publicKey.size: ${publicKey.size}")
            Log.v(
                TAG,
                "=== publicKey.toByteString().encodeHex(): ${publicKey.toByteString().encodeHex()}"
            )

            Log.v(TAG, "=== sharedKey.size: ${sharedKey.size}")
            Log.v(
                TAG,
                "=== sharedKey.toByteString().encodeHex(): ${sharedKey.toByteString().encodeHex()}"
            )

            publicKey = PrivateKeyEd25519(seed).publicKey().key.toByteArray()
            sharedKey = PrivateKeyEd25519(seed).sharedKey(PrivateKeyEd25519(seed).publicKey())

            Log.v(TAG, "=== publicKey.size: ${publicKey.size}")
            Log.v(
                TAG,
                "=== publicKey.toByteString().encodeHex(): ${publicKey.toByteString().encodeHex()}"
            )

            Log.v(TAG, "=== sharedKey.size: ${sharedKey.size}")
            Log.v(
                TAG,
                "=== sharedKey.toByteString().encodeHex(): ${sharedKey.toByteString().encodeHex()}"
            )


            val workchain: Int = 0
            val walletId: Int = WalletContract.DEFAULT_WALLET_ID + workchain

            fun createDataInit() = CellBuilder.createCell {
                storeUInt(0, 32) // seqno
                storeUInt(walletId, 32)
                storeBytes(PrivateKeyEd25519(seed).publicKey().key.toByteArray())
                storeBit(false) // plugins
            }

            fun createDataInit2(): Cell = buildCell {
                storeUInt(walletId, 32) // stored_subwallet
                storeUInt(0, 64) // last_cleaned
                storeBytes(PrivateKeyEd25519(seed).publicKey().key.toByteArray())
                storeTlb(HashMapE.tlbCodec(16, Cell.tlbCodec()), HashMapE.empty()) // old_queries
            }


            val stateInit: StateInit = StateInit(
//                code = CODE,
                code = CODE_V4R2,
                data = createDataInit()
            )
            address = buildCell { storeTlb(StateInit, stateInit) }.hash()
            val addrStd = AddrStd(workchainId, address)
            Log.v(TAG, "=== addrStd: ${addrStd.toString()}")
            Log.v(TAG, "=== addrStd: ${addrStd.toString(false)}")
            Log.v(TAG, "=== addrStd: ${addrStd.toString(true)}")


            val isBasicSeed = Mnemonic.isBasicSeed(privateKey)        // false
            Log.v(TAG, "=== isBasicSeed: $isBasicSeed")
            val isPasswordSeed = Mnemonic.isPasswordSeed(privateKey)  // false
            Log.v(TAG, "=== isPasswordSeed: $isPasswordSeed")

            fulfillBalance()

            return true
        }
        return false
    }

    private fun fulfillBalance() {
        val job = viewModelScope.launch {
            balance = withContext(Dispatchers.IO) {
                val accountAddress: MsgAddressInt = AddrStd(workchainId, address)
                val accountState: FullAccountState = liteClient.getAccountState(accountAddress)
                Log.v(TAG, "*** accountState: <${accountState}>")
                Log.v(TAG, "*** accountState.account.value: <${accountState.account.value}>")
                val accountInfo: AccountInfo = accountState.account.value as AccountInfo
                Log.v(TAG, "*** account: <${accountInfo}>")
                Log.v(TAG, "*** accountInfo.isActive: <${accountInfo.isActive}>")
                Log.v(TAG, "*** accountInfo.storageStat: <${accountInfo.storageStat}>")
                Log.v(TAG, "*** accountInfo.storage: <${accountInfo.storage}>")
                Log.v(TAG, "*** accountInfo.storage.state: <${accountInfo.storage.state}>")
                Log.v(TAG, "*** accountInfo.storage.balance: <${accountInfo.storage.balance}>")
                val amount: VarUInteger = accountInfo.storage.balance.coins.amount
                Log.v(TAG, "*** amount: <${amount}>")
                val amountValue: BigInteger = amount.value
                Log.v(TAG, "*** amountValue: <${amountValue}>")
                amountValue.toLong()
                Log.v(TAG, "*** amountValue.toLong(): <${amountValue.toLong()}>")
                amountValue.toLong()
            }
            isLoading = false
        }
    }

    fun clearSeed() {
        mnemonic = List(wordCount) { "" }
    }

    fun loadAccount() {
        // Do an asynchronous operation to fetch users.
        val job = viewModelScope.launch(
            context = EmptyCoroutineContext,
        ) {

        }
    }

    fun loadTransactions() {
        // if a task fails, cancel others
        val job = scope.launch {
            withContext(Dispatchers.IO) {

            }
        }
    }

    suspend fun loadTransactions2() {
        // if a task fails, continue with others
        val job = supervisorScope {
            launch {
                withContext(Dispatchers.IO) {
                }
            }
            launch {
                withContext(Dispatchers.IO) {

                }
            }
        }
    }

}