package ntu.mdp.android.mdptestkotlin

import android.app.Application
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.SharedPreferences
import ntu.mdp.android.mdptestkotlin.bluetooth.BluetoothClient
import ntu.mdp.android.mdptestkotlin.bluetooth.BluetoothConnectionManager
import ntu.mdp.android.mdptestkotlin.bluetooth.BluetoothServer

class App: Application() {

    companion object {
        const val BLUETOOTH_UUID = "00001101-0000-1000-8000-00805F9B34FB"
        var SEND_ARENA_COMMAND = "sendArena"
        var FORWARD_COMMAND = "f"
        var REVERSE_COMMAND = "r"
        var TURN_LEFT_COMMAND = "tl"
        var TURN_RIGHT_COMMAND = "tr"
        var BLUETOOTH_CONNECTED_DEVICE = "-"
        const val ANIMATOR_DURATION = 200L
        const val FAST_SIM_DELAY = 40L
        const val SLOW_SIM_DELAY = 320L

        var appTheme: Int = R.style.AppTheme
        var socket: BluetoothSocket? = null
        var bluetoothServerThread: BluetoothServer? = null
        var bluetoothClientThread: BluetoothClient? = null
        var bluetoothConnectionManagerThread: BluetoothConnectionManager? = null

        // Persistent Data
        lateinit var sharedPreferences: SharedPreferences
        var autoUpdateArena = false
        var darkMode = false
        var isSimple = false
        var usingAmd = true
        var testExplore = false
        var plotPathChosen = false
        var plotSearch = false
        var allowDiagonalExploration = false
        var fastSimulation = false
        var simulationDelay = SLOW_SIM_DELAY
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = this.getSharedPreferences(getString(R.string.app_pref_key), Context.MODE_PRIVATE)
        SEND_ARENA_COMMAND = sharedPreferences.getString(getString(R.string.app_pref_send_arena), getString(R.string.send_arena_default))!!
        FORWARD_COMMAND = sharedPreferences.getString(getString(R.string.app_pref_forward), getString(R.string.forward_default))!!
        REVERSE_COMMAND = sharedPreferences.getString(getString(R.string.app_pref_reverse), getString(R.string.reverse_default))!!
        TURN_LEFT_COMMAND = sharedPreferences.getString(getString(R.string.app_pref_turn_left), getString(R.string.turn_left_default))!!
        TURN_RIGHT_COMMAND = sharedPreferences.getString(getString(R.string.app_pref_turn_right), getString(R.string.turn_right_default))!!

        darkMode = sharedPreferences.getBoolean(getString(R.string.app_pref_dark_mode), false)
        if (darkMode) appTheme = R.style.AppTheme_Dark
        isSimple = sharedPreferences.getBoolean(getString(R.string.app_pref_sad_mode), false)
        autoUpdateArena = sharedPreferences.getBoolean(getString(R.string.app_pref_auto_update), true)
        usingAmd = sharedPreferences.getBoolean(getString(R.string.app_pref_using_amd), true)
        testExplore = sharedPreferences.getBoolean(getString(R.string.app_pref_test_explore), false)
        plotPathChosen = sharedPreferences.getBoolean(getString(R.string.app_pref_plot_path_chosen), false)
        plotSearch = sharedPreferences.getBoolean(getString(R.string.app_pref_plot_search), false)
        allowDiagonalExploration = sharedPreferences.getBoolean(getString(R.string.app_pref_diagonal_exploration), false)
        fastSimulation = sharedPreferences.getBoolean(getString(R.string.app_pref_fast_simulation), false)
        simulationDelay = if (fastSimulation) FAST_SIM_DELAY else SLOW_SIM_DELAY
    }
}