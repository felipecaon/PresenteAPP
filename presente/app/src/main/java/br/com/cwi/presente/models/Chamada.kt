package br.com.cwi.presente.models

import android.net.wifi.p2p.WifiP2pDevice
import android.provider.ContactsContract
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList



class Chamada(val macAddress: String)  {
    var data: ContactsContract.Data? = null

constructor() : this("")
}