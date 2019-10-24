package cn.lag.lag.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import cn.lag.lag.R
import cn.lag.lag.fragment.*
import cn.lag.lag.model.ModelManager
import cn.lag.lag.modelImpl.IFootClick
import cn.lag.lag.modelImpl.IFootModel
import cn.lag.lag.util.ToastUtils
import com.heaven.easyframework.fragment.BaseFragmentActivity
import com.heaven.easyframework.fragment.base.BaseFragment
import com.heaven.easyframework.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_activity_main.*

/**
 * 项目名称：Android-Test
 * 类名：cn.lag.lag.fragment
 * 创建人：Heaven.li
 * 创建时间：2019-10-15 14:49
 * 备注：
 */
class MainFragmentActivity : BaseFragmentActivity() {


    private val HOMEPAGE = 0
    private val ENTERPRISEPAGE = 1
    private val INFORMATIONPAGE = 2
    private val SERVERPAGE = 3

    private var exitTime: Long = 0
    private var mHomeFragment : BaseFragment? = null
    private var mEnterpriseFragment : BaseFragment? = null
    private var mInformationFragment : BaseFragment? = null
    private var mServerFragment : BaseFragment? = null
    private var mCurFragment : BaseFragment? = null

    private var fragmentManager : FragmentManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_activity_main)
        var footBar: IFootModel = ModelManager.get().getFootBar()
        footBar.setFootClick(this, object : IFootClick {
            override fun onFootClick(whichBtn: Int) {
                when(whichBtn){
                    HOMEPAGE ->{
                        getChangeFragmentPage(getDetailFragment(HOMEPAGE))
                    }
                    ENTERPRISEPAGE ->{
                        getChangeFragmentPage(getDetailFragment(ENTERPRISEPAGE))
                    }
                    INFORMATIONPAGE ->{
                        getChangeFragmentPage(getDetailFragment(INFORMATIONPAGE))
                    }
                    SERVERPAGE ->{
                        getChangeFragmentPage(getDetailFragment(SERVERPAGE))
                    }
                }
            }
        })
        fragmentManager = supportFragmentManager
        getChangeFragmentPage(getDetailFragment(HOMEPAGE))
    }

    fun getDetailFragment(page : Int) : BaseFragment{
        when(page){
            HOMEPAGE ->{
                if (null == mHomeFragment)
                    mHomeFragment = HomeFragment(this)
                return mHomeFragment as HomeFragment
            }
            ENTERPRISEPAGE ->{
                if (null == mEnterpriseFragment)
                    mEnterpriseFragment = EnterpriseFragment()
                return mEnterpriseFragment as EnterpriseFragment
            }
            INFORMATIONPAGE ->{
                if (null == mInformationFragment)
                    mInformationFragment = InformationFragment()
                return mInformationFragment as InformationFragment
            }
            SERVERPAGE ->{
                if (null == mServerFragment)
                    mServerFragment = ServerFragment()
                return mServerFragment as ServerFragment
            }
            else -> return NullFragment()
        }
    }

    fun getChangeFragmentPage(mFragment : BaseFragment){
        if (mCurFragment != mFragment){
            var trac : FragmentTransaction? = fragmentManager?.beginTransaction()
            if (fragmentManager?.findFragmentById(mFragment.id) == null){
                trac?.add(R.id.lsfl_home_content,mFragment)
            }
            trac?.show(mFragment)
            if (mCurFragment != null){
                trac?.hide(mCurFragment!!)
            }
            trac?.commit()
            mCurFragment = mFragment
        }

    }
    //    override fun onBackPressed() {
//        super.onBackPressed()
//    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event!!.getRepeatCount() == 0){
            exit()
//            overridePendingTransition(R.anim.finish_out, R.anim.finish_in)
        }
        return false
    }
    fun exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtils.showLongToast(this,R.string.click_exit_app)
            exitTime = System.currentTimeMillis()
        } else {
            System.exit(1)
        }
    }

}