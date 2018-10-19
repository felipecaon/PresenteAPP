package br.com.cwi.presente.activities

import android.content.Context
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.cwi.presente.R
import br.com.cwi.presente.services.ChamadaService
import br.com.cwi.presente.utils.UserHolder
import kotlinx.android.synthetic.main.activity_iniciar_chamada.*

class IniciarChamadaActivity : AppCompatActivity() {

    var isWifiP2pEnabled = false

    private val intentFilter = IntentFilter()
    private lateinit var mChannel: WifiP2pManager.Channel
    private lateinit var mManager: WifiP2pManager
    private lateinit var receiver: WiFiDirectBroadcastReceiver

    var peers : ArrayList<WifiP2pDevice> = arrayListOf()

    var peerListener : WifiP2pManager.PeerListListener = WifiP2pManager.PeerListListener{
        if(!it.deviceList.equals(peers)){
            peers.clear()
            peers.addAll(it.deviceList)
            ChamadaService.retrieveAulasFromFirebase(peers)
            Toast.makeText(this, "Presenças confirmadas", Toast.LENGTH_LONG).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_chamada)
        this@IniciarChamadaActivity.title = "Wifi Direct"

        val extras = intent.extras
        if (extras != null) {
            UserHolder.aulaNome = extras.getString ("nome")
        }


        // Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)

        mManager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        mChannel = mManager.initialize(this, mainLooper, null)


        discoverButton.setOnClickListener {
            mManager.discoverPeers(mChannel, object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    Toast.makeText(this@IniciarChamadaActivity, "Discover started", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(reasonCode: Int) {
                    Toast.makeText(this@IniciarChamadaActivity, "Discover failure", Toast.LENGTH_LONG).show()
                }
            })
        }

        saveButton.setOnClickListener {
            ChamadaService.retrieveAulasFromFirebase(peers)
        }

    }

    public override fun onResume() {
        super.onResume()
        receiver = WiFiDirectBroadcastReceiver(mManager, mChannel, this)
        registerReceiver(receiver, intentFilter)
    }

    public override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }
}
