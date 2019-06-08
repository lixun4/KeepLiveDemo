package keeplive.zune.com.keeplivedemo

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.support.v4.app.JobIntentService

/**
 * Created by leigong2 on 2018-06-16 016.
 */
class BindService: Service() {
    private var sendMsgThread: Thread? = null
    private var running = true
    private val timeSpan = 1 * 60 * 1000 //TODO: need to change to a reasonable value
    private val requestTimes = 15//设置请求百度定位次数。超过该次数关闭服务停止请求。
    private var mHandler: Handler? = Handler(Handler.Callback {
        TxtLog.writeDataToSDCard("请求定位的handler开始干活啦7.0以下")
        false
    })
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        print("zune: 测试bindservice")
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

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        print("zune: 测试bindservice start")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        print("zune: 测试bindservice onDestroy")
    }
}