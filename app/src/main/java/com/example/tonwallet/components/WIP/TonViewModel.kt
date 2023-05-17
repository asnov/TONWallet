package com.example.tonwallet.components.WIP

import android.util.Log
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
import org.ton.api.pk.PrivateKeyEd25519
import org.ton.bitstring.BitString
import org.ton.block.AddrStd
import org.ton.block.StateInit
import org.ton.boc.BagOfCells
import org.ton.cell.Cell
import org.ton.cell.CellBuilder
import org.ton.cell.buildCell
import org.ton.contract.wallet.WalletContract
import org.ton.contract.wallet.WalletV3R2Contract.Companion.CODE
import org.ton.crypto.Ed25519
import org.ton.crypto.encodeHex
import org.ton.crypto.encoding.base64
import org.ton.crypto.hex
import org.ton.hashmap.HashMapE
import org.ton.mnemonic.Mnemonic
import org.ton.tl.ByteString
import org.ton.tl.ByteString.Companion.toByteString
import org.ton.tlb.constructor.tlbCodec
import org.ton.tlb.storeTlb
import kotlin.coroutines.EmptyCoroutineContext

private const val TAG = "TonViewModel"

class TonViewModel : ViewModel() {
    private val userLiveData: MutableLiveData<WalletUiState> = MutableLiveData<WalletUiState>()

    private val _uiState = MutableStateFlow(WalletUiState())
    val uiState: StateFlow<WalletUiState> = _uiState.asStateFlow()

    private val scope = CoroutineScope(Job() + Dispatchers.Main) //  + exceptionHandler ???
    val scopeFailureProtected = CoroutineScope(SupervisorJob())

    // org/ton/tonkotlinusecase/contracts/wallet/WalletV4R2.kt:35
    val CODE_V4R2: Cell = BagOfCells(
        base64("te6cckECFAEAAtQAART/APSkE/S88sgLAQIBIAIDAgFIBAUE+PKDCNcYINMf0x/THwL4I7vyZO1E0NMf0x/T//QE0VFDuvKhUVG68qIF+QFUEGT5EPKj+AAkpMjLH1JAyx9SMMv/UhD0AMntVPgPAdMHIcAAn2xRkyDXSpbTB9QC+wDoMOAhwAHjACHAAuMAAcADkTDjDQOkyMsfEssfy/8QERITAubQAdDTAyFxsJJfBOAi10nBIJJfBOAC0x8hghBwbHVnvSKCEGRzdHK9sJJfBeAD+kAwIPpEAcjKB8v/ydDtRNCBAUDXIfQEMFyBAQj0Cm+hMbOSXwfgBdM/yCWCEHBsdWe6kjgw4w0DghBkc3RyupJfBuMNBgcCASAICQB4AfoA9AQw+CdvIjBQCqEhvvLgUIIQcGx1Z4MesXCAGFAEywUmzxZY+gIZ9ADLaRfLH1Jgyz8gyYBA+wAGAIpQBIEBCPRZMO1E0IEBQNcgyAHPFvQAye1UAXKwjiOCEGRzdHKDHrFwgBhQBcsFUAPPFiP6AhPLassfyz/JgED7AJJfA+ICASAKCwBZvSQrb2omhAgKBrkPoCGEcNQICEekk30pkQzmkD6f+YN4EoAbeBAUiYcVnzGEAgFYDA0AEbjJftRNDXCx+AA9sp37UTQgQFA1yH0BDACyMoHy//J0AGBAQj0Cm+hMYAIBIA4PABmtznaiaEAga5Drhf/AABmvHfaiaEAQa5DrhY/AAG7SB/oA1NQi+QAFyMoHFcv/ydB3dIAYyMsFywIizxZQBfoCFMtrEszMyXP7AMhAFIEBCPRR8qcCAHCBAQjXGPoA0z/IVCBHgQEI9FHyp4IQbm90ZXB0gBjIywXLAlAGzxZQBPoCFMtqEssfyz/Jc/sAAgBsgQEI1xj6ANM/MFIkgQEI9Fnyp4IQZHN0cnB0gBjIywXLAlAFzxZQA/oCE8tqyx8Syz/Jc/sAAAr0AMntVGliJeU=")
    ).first()

    // org/ton/tonkotlinusecase/contracts/wallet/HighloadWallet.kt:35
    val CODE_HILO: Cell = BagOfCells(
        hex("B5EE9C724101090100E5000114FF00F4A413F4BCF2C80B010201200203020148040501EAF28308D71820D31FD33FF823AA1F5320B9F263ED44D0D31FD33FD3FFF404D153608040F40E6FA131F2605173BAF2A207F901541087F910F2A302F404D1F8007F8E16218010F4786FA5209802D307D43001FB009132E201B3E65B8325A1C840348040F4438AE63101C8CB1F13CB3FCBFFF400C9ED54080004D03002012006070017BD9CE76A26869AF98EB85FFC0041BE5F976A268698F98E99FE9FF98FA0268A91040207A0737D098C92DBFC95DD1F140034208040F4966FA56C122094305303B9DE2093333601926C21E2B39F9E545A")
    ).first()

    // ton-kotlin-contract-jvm-0.3.0-SNAPSHOT-sources.jar!/commonMain/org/ton/contract/wallet/WalletV3Contract.kt:88
    val CODE_V3R2: Cell =
        Cell("FF0020DD2082014C97BA218201339CBAB19F71B0ED44D0D31FD31F31D70BFFE304E0A4F2608308D71820D31FD31FD31FF82313BBF263ED44D0D31FD31FD3FFD15132BAF2A15144BAF2A204F901541055F910F2A3F8009320D74A96D307D402FB00E8D101A4C8CB1FCB1FCBFFC9ED54")

    var wordCount = 24
    var mnemonic: List<String> = List(wordCount) { "" }
    lateinit var seed: ByteArray
    lateinit var privateKey: ByteArray
    lateinit var publicKey: ByteArray
    lateinit var sharedKey: ByteArray

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


            2
            val stateInit: StateInit = StateInit(
//                code = CODE,
                code = CODE_V4R2,
                data = createDataInit()
            )
            val address: BitString = buildCell { storeTlb(StateInit, stateInit) }.hash()
            val addrStd = AddrStd(
                workchainId = 0,
                address = address,
            )
            Log.v(TAG, "=== addrStd: ${addrStd.toString()}")
            Log.v(TAG, "=== addrStd: ${addrStd.toString(false)}")
            Log.v(TAG, "=== addrStd: ${addrStd.toString(true)}")


            val isBasicSeed = Mnemonic.isBasicSeed(privateKey)        // false
            Log.v(TAG, "=== isBasicSeed: $isBasicSeed")
            val isPasswordSeed = Mnemonic.isPasswordSeed(privateKey)  // false
            Log.v(TAG, "=== isPasswordSeed: $isPasswordSeed")

            return true
        }
        return false
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