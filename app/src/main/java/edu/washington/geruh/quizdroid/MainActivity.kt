package edu.washington.geruh.quizdroid

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_topic_list.*
import android.provider.Settings


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        mainTopics.layoutManager = LinearLayoutManager(this)
//        mainTopics.adapter = RecycleAdapter()
        checkInternetConnection()

    }

     override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent(this, PreferenceActivity::class.java)
        startActivity(intent)
        return true
    }

    private fun checkInternetConnection() {
        val builder = AlertDialog.Builder(this@MainActivity)
        if (airplaneModeTest(this@MainActivity)) {
            builder.apply {
                setTitle("Airplane Mode")
                setMessage("You set Airplane Mode on, would you like to turn it off?")
                setPositiveButton("Yes") { _, _ ->
                    startActivityForResult(Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS), 0)
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        } else if (networkTest() == false) {
            builder.apply {
                setTitle("No Internet Connection")
                setMessage("You have no access to the internet")
                setNeutralButton("Okay") { dialog, _ ->
                    dialog.dismiss()
                }
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun networkTest(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun airplaneModeTest(context: Context): Boolean {
        return Settings.System.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) != 0

    }
}
