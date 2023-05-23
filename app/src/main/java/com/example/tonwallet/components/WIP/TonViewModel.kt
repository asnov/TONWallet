package com.example.tonwallet.components.WIP

import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tonwallet.TransactionView
import io.ktor.util.encodeBase64
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
import org.ton.block.IntMsgInfo
import org.ton.block.Message
import org.ton.block.MsgAddressInt
import org.ton.block.StateInit
import org.ton.block.Transaction
import org.ton.block.TransactionAux
import org.ton.block.VarUInteger
import org.ton.boc.BagOfCells
import org.ton.cell.Cell
import org.ton.cell.CellBuilder
import org.ton.cell.EmptyCell.bits
import org.ton.cell.buildCell
import org.ton.contract.wallet.WalletContract
import org.ton.crypto.Ed25519
import org.ton.crypto.encoding.base64
import org.ton.crypto.hex
import org.ton.hashmap.HashMapE
import org.ton.lite.client.LiteClient
import org.ton.lite.client.internal.FullAccountState
import org.ton.lite.client.internal.TransactionId
import org.ton.lite.client.internal.TransactionInfo
import org.ton.mnemonic.Mnemonic
import org.ton.tlb.CellRef
import org.ton.tlb.constructor.tlbCodec
import org.ton.tlb.storeTlb
import java.math.BigInteger
import java.net.URL
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.properties.Delegates


private const val TAG = "TonViewModel"

fun AddrStd.isEmpty(): Boolean = workchainId == 0 && address == BitString(256)


open class TonViewModel(val isPreview: Boolean = false) : ViewModel() {

    lateinit var window: Window
    fun segure() {
        if (isPreview) return
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    fun unsegure() {
        if (isPreview) return
        window.clearFlags(
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

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
    lateinit var accountState: FullAccountState
    var balance by Delegates.notNull<Long>()
    private var transactionList: List<TransactionInfo> = mutableStateListOf()
    var transViewList: List<TransactionView> = mutableStateListOf()
    var transactionId: String? = null

    protected var isLoading = true


    fun addressShort(
        addrString: String = AddrStd(0, address).toString(true)
    ): String {
        return addrString.substring(0, 4) + "…" + addrString.substring(addrString.length - 4)
    }

    fun addressFull(): String {
        return AddrStd(0, address).toString(true)
    }

    fun addressFullTwoLines(): String {
        val addressString = addressFull()
        return addressString.substring(0, addressString.length / 2) +
                "\n" + addressString.substring(addressString.length / 2)
    }

    fun balanceInteger(balanceParam: Long = balance): String {
        return (balanceParam / 1_000_000_000).toString()
    }

    fun balanceFractional(balanceParam: Long = balance): Long {
        val nanotons = balanceParam % 1_000_000_000
        return nanotons // .toString().take(4).padEnd(4, '0')
    }

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
            address = AddrStd.parse("EQCD39VS5jcptHL8vMjEXrzGaRcCVYto7HUn4bpAOg8xqB2N").address
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
        runBlocking {
            mnemonic = Mnemonic.generate()
        }
        val job = viewModelScope.launch {
            isSeedValid(mnemonic)
        }
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
//            publicKey = PrivateKeyEd25519(seed).publicKey().key.toByteArray()
//            sharedKey = PrivateKeyEd25519(seed).sharedKey(PrivateKeyEd25519(seed).publicKey())

            val workchain = 0
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


            val stateInit = StateInit(
//                code = CODE,
                code = CODE_V4R2,
                data = createDataInit()
            )
            address = buildCell { storeTlb(StateInit, stateInit) }.hash()

            fulfillBalance()
            return true
        }
        return false
    }

    fun fulfillBalance() {
        val job = viewModelScope.launch(context = Dispatchers.IO) {
            balance = withContext(Dispatchers.IO) {
                val accountAddress: MsgAddressInt = AddrStd(workchainId, address)
                accountState = liteClient.getAccountState(accountAddress)
                try {
                    val accountInfo: AccountInfo = accountState.account.value as AccountInfo
                    val amount: VarUInteger = accountInfo.storage.balance.coins.amount
                    val amountValue: BigInteger = amount.value
                    amountValue.toLong()
                } catch (e: Exception) {
                    Log.v(TAG, "*** Exception: <${e}>")
                    Log.v(TAG, "*** message: ${e.message}")
                    Log.v(TAG, "*** localizedMessage: ${e.localizedMessage}")
                    0
                }
            }
            isLoading = false
//            loadTransactions()
            getTransactions(liteClient, accountState)
        }
    }

    fun clearSeed() {
        mnemonic = List(wordCount) { "" }
    }

    fun deleteWallet() {
        clearSeed()
        passcode = ""
        seed = ByteArray(0)
        privateKey = ByteArray(0)
        publicKey = ByteArray(0)
        sharedKey = ByteArray(0)
        workchainId = 0
        address = BitString(0)
        balance = 0
        transactionList = listOf()
        transViewList = listOf()
        transactionId = null
        isLoading = true
    }

    fun loadAccount() {
        // Do an asynchronous operation to fetch users.
        val job = viewModelScope.launch(
            context = EmptyCoroutineContext,
        ) {

        }
    }

    fun loadTransactions() {
        val lastTransactionId = accountState.lastTransactionId ?: return

        // if a task fails, cancel others
        val job = scope.launch {
            val transactionsInfo: List<TransactionInfo> = withContext(Dispatchers.IO) {
                liteClient.getTransactions(
                    AddrStd(workchainId, address),
                    lastTransactionId,
                    Int.MAX_VALUE,
                )
            }
            Log.v(TAG, "*** transactionsInfo.size: <${transactionsInfo.size}>")
            Log.v(TAG, "*** transactionsInfo: <${transactionsInfo}>")
            transactionsInfo.forEachIndexed { index, transactionInfo ->
                Log.v(TAG, "*** $index transactionInfo: <${transactionInfo}>")

                transactionInfo.id
                Log.v(TAG, "*** $index transactionInfo.id: <${transactionInfo.id}>")
                Log.v(TAG, "*** $index transactionInfo.id: <${transactionInfo.id.hash}>") // ???
                Log.v(TAG, "*** $index transactionInfo.id: <${transactionInfo.id.lt}>")

                transactionInfo.blockId
                Log.v(TAG, "*** $index transactionInfo.blockId: <${transactionInfo.blockId}>")

                transactionInfo.transaction
                Log.v(
                    TAG,
                    "*** $index transactionInfo.transaction: <${transactionInfo.transaction}>"
                )
                Log.v(
                    TAG,
                    "*** $index transactionInfo.transaction: <${transactionInfo.transaction.value}>"
                )

                val transaction: Transaction = transactionInfo.transaction.value

                Log.v(TAG, "*** $index transaction.lt: <${transaction.lt}>")
                transaction.accountAddr
                Log.v(TAG, "*** $index transaction.accountAddr: <${transaction.accountAddr}>")
                transaction.now
                Log.v(TAG, "*** $index transaction.now: <${transaction.now}>")
                transaction.description
                Log.v(TAG, "*** $index transaction.description: <${transaction.description}>")
                transaction.totalFees
                Log.v(TAG, "*** $index transaction.totalFees: <${transaction.totalFees}>")
                transaction.outMsgCnt
                Log.v(TAG, "*** $index transaction.outMsgCnt: <${transaction.outMsgCnt}>")

                val tranAux: TransactionAux = transaction.r1.value
                val inMsg: CellRef<Message<Cell>>? = tranAux.inMsg.value
                Log.v(TAG, "*** $index message: <${inMsg}>")
                if (inMsg != null) {
                    Log.v(TAG, "*** $index inMsg.value: <${inMsg.value}>")
                    Log.v(TAG, "*** $index inMsg.value.body: <${inMsg.value.body}>")
                    Log.v(TAG, "*** $index inMsg.value.init: <${inMsg.value.init}>")
                    Log.v(TAG, "*** $index inMsg.value.info: <${inMsg.value.info}>")
                }

                val outMsgs: HashMapE<CellRef<Message<Cell>>> = tranAux.outMsgs
                Log.v(TAG, "*** $index outMsgs: <${outMsgs}>")

                val firstOutMsg: Pair<BitString, CellRef<Message<Cell>>> = outMsgs.iterator().next()
                for (outMsg in outMsgs) {
                    Log.v(TAG, "*** $index outMsg: <${outMsg}>")
                    outMsg
                }

            }
        }
    }

    suspend fun getTransactions(liteClient: LiteClient, fullAccountState: FullAccountState) {
        var lastTransactionId = fullAccountState.lastTransactionId ?: return

        for (batchIndex in 0..Int.MAX_VALUE) {
            Log.v(TAG, ">>> batch:${batchIndex}, id=${lastTransactionId}")

            val transactionListToAdd: List<TransactionInfo> = try {
                liteClient.getTransactions(
                    fullAccountState.address,
                    lastTransactionId,
                    Int.MAX_VALUE
                )
            } catch (e: Exception) {
                Log.v(TAG, ">>> Exception: ${e.message}")
                emptyList()
            }
            Log.v(TAG, ">>> ${transactionListToAdd.size} transactions found:")

            transactionListToAdd.isEmpty() && break

            transactionList = transactionList.plus(transactionListToAdd)

            this.updateTransactionView(transactionListToAdd)

            val lastTransaction = transactionListToAdd.last().transaction.value
            lastTransactionId =
                TransactionId(lastTransaction.prevTransHash, lastTransaction.prevTransLt.toLong())
        }
        Log.v(TAG, ">>> transactionList.size: ${transactionList.size}")
        Log.v(TAG, ">>> transViewList.size: ${transViewList.size}")
        isLoading = false
    }

    private fun updateTransactionView(transactionList: List<TransactionInfo>) {
        transactionList.forEach { transactionInfo ->
            val now = transactionInfo.transaction.value.now.toLong()
            val localDateTime = LocalDateTime.ofEpochSecond(now, 0, ZoneOffset.UTC)
            val r1Value: TransactionAux = transactionInfo.transaction.value.r1.value
            val isIncome = transactionInfo.transaction.value.outMsgCnt == 0
            val header = localDateTime.format(DateTimeFormatter.ofPattern("MMM d"))

            val transactionView = TransactionView(
                id = transactionInfo.id.hash.toByteArray().encodeBase64(),
                now = now,
                date = localDateTime,
                header = if (transViewList.isNotEmpty() && transViewList.last().header == header) ""
                else header,
                amount = 0,
                isIncome = isIncome,
                address = "",
                fee = transactionInfo.transaction.value.totalFees.coins.amount.toLong(),
                description = "",
            )
            var infoCasted: IntMsgInfo
            if (isIncome) {
                r1Value.inMsg.value?.let { inMsg ->
                    try {
                        infoCasted = inMsg.value.info as IntMsgInfo
                        transactionView.address = MsgAddressInt.toString(infoCasted.src)
                        transactionView.amount = infoCasted.value.coins.amount.toLong()
                        val body = inMsg.value.body.x ?: inMsg.value.body.y?.value ?: Cell()

                        if (body.bits.size >= 32) {
                            transactionView.description = body.bits
                                .slice(32 until body.bits.size)
                                .toByteArray()
                                .decodeToString()
                        }


                    } catch (e: Exception) {
                        Log.wtf(TAG, "IntMsgInfo casted error: ${e.message}")
                    }
                } ?: {
                    Log.wtf(TAG, "There is no inMsg in transaction ${transactionView}")
                }
            } else {
                infoCasted = r1Value.outMsgs.first().second.value.info as IntMsgInfo
                transactionView.address = MsgAddressInt.toString(infoCasted.dest)
                transactionView.amount = infoCasted.value.coins.amount.toLong()
            }

//            transViewList.plus(transactionInfo) // TODO: report bug doesn't check for type matching
            transViewList = transViewList.plus(transactionView)
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