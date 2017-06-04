package me.williamhester.reddit.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

import javax.inject.Inject

import me.williamhester.reddit.BreaditApplication
import me.williamhester.reddit.messages.StartActivityMessage

abstract class BaseActivity : AppCompatActivity() {
  @Inject lateinit internal var bus: EventBus

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layoutId)

    val application = applicationContext as BreaditApplication
    application.applicationComponent.inject(this)
  }

  override fun onStart() {
    super.onStart()

    bus.register(this)
  }

  override fun onStop() {
    super.onStop()

    bus.unregister(this)
  }

  @Subscribe
  fun startActivity(request: StartActivityMessage) {
    request.startActivity(this)
  }

  protected abstract val layoutId: Int
}
