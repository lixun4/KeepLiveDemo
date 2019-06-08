package keeplive.zune.com.keeplivedemo

import android.content.Intent
import android.os.Handler
import android.os.Message
import android.support.v4.app.JobIntentService

/**
 * Created by leigong2 on 2018-06-16 016.
 */
class BindOService: JobIntentService() {
    private var sendMsgThread: Thread? = null
    private var running = true
    private val timeSpan = 1 * 60 * 1000 //TODO: need to change to a reasonable value
    private var mHandler: Handler? = Handler(Handler.Callback {
        TxtLog.writeDataToSDCard("请求定位的handler开始干活啦7.0及以上")
        false
    })
    override fun onHandleWork(intent: Intent) {

    }

    override fun onCreate() {
        super.onCreate()
        sendMsgThread = object : Thread() {
            override fun run() {
                while (running && mHandler != null) {
                    mHandler!!.sendMessage(Message())

                    try {
                        Thread.sleep(timeSpan.toLong())
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }
        }
        sendMsgThread!!.start()
        println("zune: 测试bindservice")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("zune: 测试bindservice start")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        running=false
        TxtLog.writeDataToSDCard("zune: 测试bindservice onDestroy")
        println("zune: 测试bindservice onDestroy")
    }
}