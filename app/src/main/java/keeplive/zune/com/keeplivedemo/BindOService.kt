package keeplive.zune.com.keeplivedemo

import android.content.Intent
import android.os.Handler
import android.os.Message
import android.support.v4.app.JobIntentService

/**
 * Created by leigong2 on 2018-06-16 016.
 */
class BindOService: JobIntentService() {
    private var mHandler: Handler? = null
    private var sendMsgThread: Thread? = null
    private var running = true
    override fun onHandleWork(intent: Intent) {

    }

    override fun onCreate() {
        super.onCreate()
        println("zune: 测试bindservice")
        mHandler = Handler(Handler.Callback {
           TxtLog.writeDataToSDCard("123455")
            false
        })

        sendMsgThread = object : Thread() {
            override fun run() {
                while (running && mHandler != null) {
                    mHandler!!.sendMessage(Message())

                    try {
                        Thread.sleep(1*60*1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }
        }
        (sendMsgThread as Thread).start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        TxtLog.writeDataToSDCard("zune: 测试bindservice start")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        running=false
        TxtLog.writeDataToSDCard("zune: 测试bindservice onDestroy")
        println("zune: 测试bindservice onDestroy")
    }
}