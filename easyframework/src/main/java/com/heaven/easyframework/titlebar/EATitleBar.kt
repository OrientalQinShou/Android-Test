package com.heaven.easyframework.titlebar

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.heaven.easyframework.R
import com.heaven.easyframework.titlebar.statusbar.StatusBarUtils
import com.heaven.easyframework.titlebar.widget.CommonTitleBar
import com.heaven.easyframework.util.EAScreenUtils

/**
 * 项目名称：EasyFramework
 * 类名：com.heaven.easyframework.util
 * 创建人：Heaven.li
 * 创建时间：2019/10/9 11:14
 * 备注：
 */
class EATitleBar : RelativeLayout ,View.OnClickListener{


    private var viewStatusBarFill: View? = null                     // 状态栏填充视图
    private var viewBottomLine: View? = null                        // 分隔线视图
    private var viewBottomShadow: View? = null                      // 底部阴影
    private var rlMain: RelativeLayout? = null                      // 主视图

    private var tvLeft: TextView? = null                            // 左边TextView
    private var btnLeft: ImageButton? = null                        // 左边ImageButton
    private var viewCustomLeft: View? = null
    private var tvRight: TextView? = null                           // 右边TextView
    private var btnRight: ImageButton? = null                       // 右边ImageButton
    private var viewCustomRight: View? = null
    private var llMainCenter: LinearLayout? = null
    private var tvCenter: TextView? = null                          // 标题栏文字
    private var tvCenterSub: TextView? = null                       // 副标题栏文字
    private var progressCenter: ProgressBar? = null                 // 中间进度条,默认隐藏
    private var rlMainCenterSearch: RelativeLayout? = null          // 中间搜索框布局
    private var etSearchHint: EditText? = null
    private var ivSearch: ImageView? = null
    private var ivVoice: ImageView? = null
    private var centerCustomView: View? = null                      // 中间自定义视图

    private var fillStatusBar: Boolean = false                      // 是否撑起状态栏, true时,标题栏浸入状态栏
    private var titleBarColor: Int = 0                          // 标题栏背景颜色
    private var titleBarHeight: Int = 0                         // 标题栏高度
    private var statusBarColor: Int = 0                         // 状态栏颜色
    private var statusBarMode: Int = 0                          // 状态栏模式

    private var showBottomLine: Boolean = false                     // 是否显示底部分割线
    private var bottomLineColor: Int = 0                        // 分割线颜色
    private var bottomShadowHeight: Float = 0.toFloat()                   // 底部阴影高度

    private var leftType: Int = 0                               // 左边视图类型
    private var leftText: String? = null                            // 左边TextView文字
    private var leftTextColor: Int = 0                          // 左边TextView颜色
    private var leftTextSize: Float = 0.toFloat()                         // 左边TextView文字大小
    private var leftDrawable: Int = 0                           // 左边TextView drawableLeft资源
    private var leftDrawablePadding: Float = 0.toFloat()                  // 左边TextView drawablePadding
    private var leftImageResource: Int = 0                      // 左边图片资源
    private var leftCustomViewRes: Int = 0                      // 左边自定义视图布局资源

    private var rightType: Int = 0                              // 右边视图类型
    private var rightText: String? = null                           // 右边TextView文字
    private var rightTextColor: Int = 0                         // 右边TextView颜色
    private var rightTextSize: Float = 0.toFloat()                        // 右边TextView文字大小
    private var rightImageResource: Int = 0                     // 右边图片资源
    private var rightCustomViewRes: Int = 0                     // 右边自定义视图布局资源

    private var centerType: Int = 0                             // 中间视图类型
    private var centerText: String? = null                          // 中间TextView文字
    private var centerTextColor: Int = 0                        // 中间TextView字体颜色
    private var centerTextSize: Float = 0.toFloat()                       // 中间TextView字体大小
    private var centerTextMarquee: Boolean = false                  // 中间TextView字体是否显示跑马灯效果
    private var centerSubText: String? = null                       // 中间subTextView文字
    private var centerSubTextColor: Int = 0                     // 中间subTextView字体颜色
    private var centerSubTextSize: Float = 0.toFloat()                    // 中间subTextView字体大小
    private var centerSearchEditable: Boolean = false                // 搜索框是否可输入
    private var centerSearchBgResource: Int = 0                 // 搜索框背景图片
    private var centerSearchRightType: Int = 0                  // 搜索框右边按钮类型  0: voice 1: delete
    private var centerCustomViewRes: Int = 0                    // 中间自定义布局资源

    private var PADDING_5: Int = 0
    private var PADDING_12: Int = 0

    private var listener: CommonTitleBar.OnTitleBarListener? = null
    private var doubleClickListener: CommonTitleBar.OnTitleBarDoubleClickListener? = null

    private val TYPE_LEFT_NONE = 0
    private val TYPE_LEFT_TEXTVIEW = 1
    private val TYPE_LEFT_IMAGEBUTTON = 2
    private val TYPE_LEFT_CUSTOM_VIEW = 3
    private val TYPE_RIGHT_NONE = 0
    private val TYPE_RIGHT_TEXTVIEW = 1
    private val TYPE_RIGHT_IMAGEBUTTON = 2
    private val TYPE_RIGHT_CUSTOM_VIEW = 3
    private val TYPE_CENTER_NONE = 0
    private val TYPE_CENTER_TEXTVIEW = 1
    private val TYPE_CENTER_SEARCHVIEW = 2
    private val TYPE_CENTER_CUSTOM_VIEW = 3

    private val TYPE_CENTER_SEARCH_RIGHT_VOICE = 0
    private val TYPE_CENTER_SEARCH_RIGHT_DELETE = 1

    constructor(context: Context?, attrs: AttributeSet?):super(context, attrs){
        loadAttributes(context, attrs)
        initGlobalViews(context)
        initMainViews(context)
    }


    private fun loadAttributes(context: Context?, attrs: AttributeSet?) {
        if (attrs == null || context == null)
            return
        PADDING_5 = EAScreenUtils.dp2PxInt(context, 5f)
        PADDING_12 = EAScreenUtils.dp2PxInt(context, 12f)

        val array = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleBar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // notice 未引入沉浸式标题栏之前,隐藏标题栏撑起布局
            fillStatusBar = array.getBoolean(R.styleable.CommonTitleBar_fillStatusBar, true)
        }
        titleBarColor = array.getColor(R.styleable.CommonTitleBar_titleBarColor, Color.parseColor("#ffffff"))
        titleBarHeight = array.getDimension(R.styleable.CommonTitleBar_titleBarHeight, EAScreenUtils.dp2PxInt(context, 44f).toFloat()).toInt()
        statusBarColor = array.getColor(R.styleable.CommonTitleBar_statusBarColor, Color.parseColor("#ffffff"))
        statusBarMode = array.getInt(R.styleable.CommonTitleBar_statusBarMode, 0)

        showBottomLine = array.getBoolean(R.styleable.CommonTitleBar_showBottomLine, true)
        bottomLineColor = array.getColor(R.styleable.CommonTitleBar_bottomLineColor, Color.parseColor("#dddddd"))
        bottomShadowHeight = array.getDimension(R.styleable.CommonTitleBar_bottomShadowHeight, EAScreenUtils.dp2PxInt(context, 0f).toFloat())

        leftType = array.getInt(R.styleable.CommonTitleBar_leftType, TYPE_LEFT_NONE)
        if (leftType == TYPE_LEFT_TEXTVIEW) {
            leftText = array.getString(R.styleable.CommonTitleBar_leftText)
            leftTextColor = array.getColor(R.styleable.CommonTitleBar_leftTextColor, resources.getColor(R.color.color_fa3a7a))
            leftTextSize = array.getDimension(R.styleable.CommonTitleBar_leftTextSize, EAScreenUtils.dp2PxInt(context, 16f).toFloat())
            leftDrawable = array.getResourceId(R.styleable.CommonTitleBar_leftDrawable, 0)
            leftDrawablePadding = array.getDimension(R.styleable.CommonTitleBar_leftDrawablePadding, 5f)
        } else if (leftType == TYPE_LEFT_IMAGEBUTTON) {
            leftImageResource = array.getResourceId(R.styleable.CommonTitleBar_leftImageResource, R.drawable.comm_titlebar_reback_selector)
        } else if (leftType == TYPE_LEFT_CUSTOM_VIEW) {
            leftCustomViewRes = array.getResourceId(R.styleable.CommonTitleBar_leftCustomView, 0)
        }

        rightType = array.getInt(R.styleable.CommonTitleBar_rightType, TYPE_RIGHT_NONE)
        if (rightType == TYPE_RIGHT_TEXTVIEW) {
            rightText = array.getString(R.styleable.CommonTitleBar_rightText)
            rightTextColor = array.getColor(R.styleable.CommonTitleBar_rightTextColor, resources.getColor(R.color.color_fa3a7a))
            rightTextSize = array.getDimension(R.styleable.CommonTitleBar_rightTextSize, EAScreenUtils.dp2PxInt(context, 16f).toFloat())
        } else if (rightType == TYPE_RIGHT_IMAGEBUTTON) {
            rightImageResource = array.getResourceId(R.styleable.CommonTitleBar_rightImageResource, 0)
        } else if (rightType == TYPE_RIGHT_CUSTOM_VIEW) {
            rightCustomViewRes = array.getResourceId(R.styleable.CommonTitleBar_rightCustomView, 0)
        }

        centerType = array.getInt(R.styleable.CommonTitleBar_centerType, TYPE_CENTER_NONE)
        if (centerType == TYPE_CENTER_TEXTVIEW) {
            centerText = array.getString(R.styleable.CommonTitleBar_centerText)
            centerTextColor = array.getColor(R.styleable.CommonTitleBar_centerTextColor, Color.parseColor("#333333"))
            centerTextSize = array.getDimension(R.styleable.CommonTitleBar_centerTextSize, EAScreenUtils.dp2PxInt(context, 18f).toFloat())
            centerTextMarquee = array.getBoolean(R.styleable.CommonTitleBar_centerTextMarquee, true)
            centerSubText = array.getString(R.styleable.CommonTitleBar_centerSubText)
            centerSubTextColor = array.getColor(R.styleable.CommonTitleBar_centerSubTextColor, Color.parseColor("#666666"))
            centerSubTextSize = array.getDimension(R.styleable.CommonTitleBar_centerSubTextSize, EAScreenUtils.dp2PxInt(context, 11f).toFloat())
        } else if (centerType == TYPE_CENTER_SEARCHVIEW) {
            centerSearchEditable = array.getBoolean(R.styleable.CommonTitleBar_centerSearchEditable, true)
            centerSearchBgResource = array.getResourceId(R.styleable.CommonTitleBar_centerSearchBg, R.drawable.comm_titlebar_search_gray_shape)
            centerSearchRightType = array.getInt(R.styleable.CommonTitleBar_centerSearchRightType, TYPE_CENTER_SEARCH_RIGHT_VOICE)
        } else if (centerType == TYPE_CENTER_CUSTOM_VIEW) {
            centerCustomViewRes = array.getResourceId(R.styleable.CommonTitleBar_centerCustomView, 0)
        }

        array.recycle()
    }

    private val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
    private val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT

    /**
     * 初始化全局视图
     *
     * @param context       上下文
     */
    private fun initGlobalViews(context: Context?) {
        if (context == null)
            return
        val globalParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        layoutParams = globalParams

        val transparentStatusBar = StatusBarUtils.supportTransparentStatusBar()

        // 构建标题栏填充视图
        if (fillStatusBar && transparentStatusBar) {
            val statusBarHeight = StatusBarUtils.getStatusBarHeight(context)
            viewStatusBarFill = View(context)
            viewStatusBarFill?.setId(StatusBarUtils.generateViewId())
            viewStatusBarFill?.setBackgroundColor(statusBarColor)
            val statusBarParams = RelativeLayout.LayoutParams(MATCH_PARENT, statusBarHeight)
            statusBarParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
            addView(viewStatusBarFill, statusBarParams)
        }

        // 构建主视图
        rlMain = RelativeLayout(context)
        rlMain?.setId(StatusBarUtils.generateViewId())
        rlMain?.setBackgroundColor(titleBarColor)
        val mainParams = RelativeLayout.LayoutParams(MATCH_PARENT, titleBarHeight)
        if (fillStatusBar && transparentStatusBar) {
            mainParams.addRule(RelativeLayout.BELOW, viewStatusBarFill?.id!!)
        } else {
            mainParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        }

        // 计算主布局高度
        if (showBottomLine) {
            mainParams.height = titleBarHeight - Math.max(1, EAScreenUtils.dp2PxInt(context, 0.4f))
        } else {
            mainParams.height = titleBarHeight
        }
        addView(rlMain, mainParams)

        // 构建分割线视图
        if (showBottomLine) {
            // 已设置显示标题栏分隔线,5.0以下机型,显示分隔线
            viewBottomLine = View(context)
            viewBottomLine?.setBackgroundColor(bottomLineColor)
            val bottomLineParams = RelativeLayout.LayoutParams(MATCH_PARENT, Math.max(1, EAScreenUtils.dp2PxInt(context, 0.4f)))
            bottomLineParams.addRule(RelativeLayout.BELOW, rlMain?.id!!)

            addView(viewBottomLine, bottomLineParams)
        } else if (bottomShadowHeight != 0f) {
            viewBottomShadow = View(context)
            viewBottomShadow?.setBackgroundResource(R.drawable.comm_titlebar_bottom_shadow)
            val bottomShadowParams = RelativeLayout.LayoutParams(MATCH_PARENT, EAScreenUtils.dp2PxInt(context, bottomShadowHeight))
            bottomShadowParams.addRule(RelativeLayout.BELOW, rlMain?.id!!)

            addView(viewBottomShadow, bottomShadowParams)
        }
    }

    /**
     * 初始化主视图
     *
     * @param context       上下文
     */
    private fun initMainViews(context: Context?) {
        if (context == null)
            return
        if (leftType != TYPE_LEFT_NONE) {
            initMainLeftViews(context)
        }
        if (rightType != TYPE_RIGHT_NONE) {
            initMainRightViews(context)
        }
        if (centerType != TYPE_CENTER_NONE) {
            initMainCenterViews(context)
        }
    }

    /**
     * 初始化主视图左边部分
     * -- add: adaptive RTL
     * @param context       上下文
     */
    private fun initMainLeftViews(context: Context) {
        val leftInnerParams = RelativeLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT)
        leftInnerParams.addRule(RelativeLayout.ALIGN_PARENT_START)
        leftInnerParams.addRule(RelativeLayout.CENTER_VERTICAL)

        if (leftType == TYPE_LEFT_TEXTVIEW) {
            // 初始化左边TextView
            tvLeft = TextView(context)
            tvLeft?.setId(StatusBarUtils.generateViewId())
            tvLeft?.setText(leftText)
            tvLeft?.setTextColor(leftTextColor)
            tvLeft?.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize)
            tvLeft?.setGravity(Gravity.START or Gravity.CENTER_VERTICAL)
            tvLeft?.setSingleLine(true)
            tvLeft?.setOnClickListener(this)
            // 设置DrawableLeft及DrawablePadding
            if (leftDrawable != 0) {
                tvLeft?.setCompoundDrawablePadding(leftDrawablePadding.toInt())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    tvLeft?.setCompoundDrawablesRelativeWithIntrinsicBounds(leftDrawable, 0, 0, 0)
                } else {
                    tvLeft?.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, 0, 0, 0)
                }
            }
            tvLeft?.setPadding(PADDING_12, 0, PADDING_12, 0)

            rlMain?.addView(tvLeft, leftInnerParams)

        } else if (leftType == TYPE_LEFT_IMAGEBUTTON) {
            // 初始化左边ImageButton
            btnLeft = ImageButton(context)
            btnLeft?.setId(StatusBarUtils.generateViewId())
            btnLeft?.setBackgroundColor(Color.TRANSPARENT)
            btnLeft?.setImageResource(leftImageResource)
            btnLeft?.setPadding(PADDING_12, 0, PADDING_12, 0)
            btnLeft?.setOnClickListener(this)

            rlMain?.addView(btnLeft, leftInnerParams)

        } else if (leftType == TYPE_LEFT_CUSTOM_VIEW) {
            // 初始化自定义View
            viewCustomLeft = LayoutInflater.from(context).inflate(leftCustomViewRes, rlMain, false)
            if (viewCustomLeft?.id == View.NO_ID) {
                viewCustomLeft?.setId(StatusBarUtils.generateViewId())
            }
            rlMain?.addView(viewCustomLeft, leftInnerParams)
        }
    }

    /**
     * 初始化主视图右边部分
     * -- add: adaptive RTL
     * @param context       上下文
     */
    private fun initMainRightViews(context: Context) {
        val rightInnerParams = RelativeLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT)
        rightInnerParams.addRule(RelativeLayout.ALIGN_PARENT_END)
        rightInnerParams.addRule(RelativeLayout.CENTER_VERTICAL)

        if (rightType == TYPE_RIGHT_TEXTVIEW) {
            // 初始化右边TextView
            tvRight = TextView(context)
            tvRight?.setId(StatusBarUtils.generateViewId())
            tvRight?.setText(rightText)
            tvRight?.setTextColor(rightTextColor)
            tvRight?.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize)
            tvRight?.setGravity(Gravity.END or Gravity.CENTER_VERTICAL)
            tvRight?.setSingleLine(true)
            tvRight?.setPadding(PADDING_12, 0, PADDING_12, 0)
            tvRight?.setOnClickListener(this)
            rlMain?.addView(tvRight, rightInnerParams)

        } else if (rightType == TYPE_RIGHT_IMAGEBUTTON) {
            // 初始化右边ImageBtn
            btnRight = ImageButton(context)
            btnRight?.setId(StatusBarUtils.generateViewId())
            btnRight?.setImageResource(rightImageResource)
            btnRight?.setBackgroundColor(Color.TRANSPARENT)
            btnRight?.setScaleType(ImageView.ScaleType.CENTER_INSIDE)
            btnRight?.setPadding(PADDING_12, 0, PADDING_12, 0)
            btnRight?.setOnClickListener(this)
            rlMain?.addView(btnRight, rightInnerParams)

        } else if (rightType == TYPE_RIGHT_CUSTOM_VIEW) {
            // 初始化自定义view
            viewCustomRight = LayoutInflater.from(context).inflate(rightCustomViewRes, rlMain, false)
            if (viewCustomRight?.id == View.NO_ID) {
                viewCustomRight?.setId(StatusBarUtils.generateViewId())
            }
            rlMain?.addView(viewCustomRight, rightInnerParams)
        }
    }

    /**
     * 初始化主视图中间部分
     *
     * @param context   上下文
     */
    private fun initMainCenterViews(context: Context) {
        if (centerType == TYPE_CENTER_TEXTVIEW) {
            // 初始化中间子布局
            llMainCenter = LinearLayout(context)
            llMainCenter?.setId(StatusBarUtils.generateViewId())
            llMainCenter?.setGravity(Gravity.CENTER)
            llMainCenter?.setOrientation(LinearLayout.VERTICAL)
            llMainCenter?.setOnClickListener(this)

            val centerParams = RelativeLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT)
            centerParams.marginStart = PADDING_12
            centerParams.marginEnd = PADDING_12
            centerParams.addRule(RelativeLayout.CENTER_IN_PARENT)
            rlMain?.addView(llMainCenter, centerParams)

            // 初始化标题栏TextView
            tvCenter = TextView(context)
            tvCenter?.setText(centerText)
            tvCenter?.setTextColor(centerTextColor)
            tvCenter?.setTextSize(TypedValue.COMPLEX_UNIT_PX, centerTextSize)
            tvCenter?.setGravity(Gravity.CENTER)
            tvCenter?.setSingleLine(true)
            // 设置跑马灯效果
            tvCenter?.setMaxWidth((EAScreenUtils.getScreenPixelSize(context)[0] * 3 / 5.0).toInt())
            if (centerTextMarquee) {
                tvCenter?.setEllipsize(TextUtils.TruncateAt.MARQUEE)
                tvCenter?.setMarqueeRepeatLimit(-1)
                tvCenter?.requestFocus()
                tvCenter?.setSelected(true)
            }

            val centerTextParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            llMainCenter?.addView(tvCenter, centerTextParams)

            // 初始化进度条, 显示于标题栏左边
            progressCenter = ProgressBar(context)
            progressCenter?.setIndeterminateDrawable(resources.getDrawable(R.drawable.comm_titlebar_progress_draw))
            progressCenter?.setVisibility(View.GONE)
            val progressWidth = EAScreenUtils.dp2PxInt(context, 18f)
            val progressParams = RelativeLayout.LayoutParams(progressWidth, progressWidth)
            progressParams.addRule(RelativeLayout.CENTER_VERTICAL)
            progressParams.addRule(RelativeLayout.START_OF, llMainCenter?.id!!)
            rlMain?.addView(progressCenter, progressParams)

            // 初始化副标题栏
            tvCenterSub = TextView(context)
            tvCenterSub?.setText(centerSubText)
            tvCenterSub?.setTextColor(centerSubTextColor)
            tvCenterSub?.setTextSize(TypedValue.COMPLEX_UNIT_PX, centerSubTextSize)
            tvCenterSub?.setGravity(Gravity.CENTER)
            tvCenterSub?.setSingleLine(true)
            if (TextUtils.isEmpty(centerSubText)) {
                tvCenterSub?.setVisibility(View.GONE)
            }

            val centerSubTextParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            llMainCenter?.addView(tvCenterSub, centerSubTextParams)
        } else if (centerType == TYPE_CENTER_SEARCHVIEW) {
            // 初始化通用搜索框
            rlMainCenterSearch = RelativeLayout(context)
            rlMainCenterSearch?.setBackgroundResource(centerSearchBgResource)
            val centerParams = RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            // 设置边距
            centerParams.topMargin = EAScreenUtils.dp2PxInt(context, 7f)
            centerParams.bottomMargin = EAScreenUtils.dp2PxInt(context, 7f)
            // 根据左边的布局类型来设置边距,布局依赖规则
            if (leftType == TYPE_LEFT_TEXTVIEW) {
                centerParams.addRule(RelativeLayout.END_OF, tvLeft?.id!!)
                centerParams.marginStart = PADDING_5
            } else if (leftType == TYPE_LEFT_IMAGEBUTTON) {
                centerParams.addRule(RelativeLayout.END_OF, btnLeft?.id!!)
                centerParams.marginStart = PADDING_5
            } else if (leftType == TYPE_LEFT_CUSTOM_VIEW) {
                centerParams.addRule(RelativeLayout.END_OF, viewCustomLeft?.id!!)
                centerParams.marginStart = PADDING_5
            } else {
                centerParams.marginStart = PADDING_12
            }
            // 根据右边的布局类型来设置边距,布局依赖规则
            if (rightType == TYPE_RIGHT_TEXTVIEW) {
                centerParams.addRule(RelativeLayout.START_OF, tvRight?.id!!)
                centerParams.marginEnd = PADDING_5
            } else if (rightType == TYPE_RIGHT_IMAGEBUTTON) {
                centerParams.addRule(RelativeLayout.START_OF, btnRight?.id!!)
                centerParams.marginEnd = PADDING_5
            } else if (rightType == TYPE_RIGHT_CUSTOM_VIEW) {
                centerParams.addRule(RelativeLayout.START_OF, viewCustomRight?.id!!)
                centerParams.marginEnd = PADDING_5
            } else {
                centerParams.marginEnd = PADDING_12
            }
            rlMain?.addView(rlMainCenterSearch, centerParams)

            // 初始化搜索框搜索ImageView
            ivSearch = ImageView(context)
            ivSearch?.setId(StatusBarUtils.generateViewId())
            ivSearch?.setOnClickListener(this)
            val searchIconWidth = EAScreenUtils.dp2PxInt(context, 15f)
            val searchParams = RelativeLayout.LayoutParams(searchIconWidth, searchIconWidth)
            searchParams.addRule(RelativeLayout.CENTER_VERTICAL)
            searchParams.addRule(RelativeLayout.ALIGN_PARENT_START)
            searchParams.marginStart = PADDING_12
            rlMainCenterSearch?.addView(ivSearch, searchParams)
            ivSearch?.setImageResource(R.drawable.comm_titlebar_search_normal)

            // 初始化搜索框语音ImageView
            ivVoice = ImageView(context)
            ivVoice?.setId(StatusBarUtils.generateViewId())
            ivVoice?.setOnClickListener(this)
            val voiceParams = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            voiceParams.addRule(RelativeLayout.CENTER_VERTICAL)
            voiceParams.addRule(RelativeLayout.ALIGN_PARENT_END)
            voiceParams.marginEnd = PADDING_12
            rlMainCenterSearch?.addView(ivVoice, voiceParams)
            if (centerSearchRightType == TYPE_CENTER_SEARCH_RIGHT_VOICE) {
                ivVoice?.setImageResource(R.drawable.comm_titlebar_voice)
            } else {
                ivVoice?.setImageResource(R.drawable.comm_titlebar_delete_normal)
                ivVoice?.setVisibility(View.GONE)
            }

            // 初始化文字输入框
            etSearchHint = EditText(context)
            etSearchHint?.setBackgroundColor(Color.TRANSPARENT)
            etSearchHint?.setGravity(Gravity.START or Gravity.CENTER_VERTICAL)
            etSearchHint?.setHint(resources.getString(R.string.titlebar_search_hint))
            etSearchHint?.setTextColor(Color.parseColor("#666666"))
            etSearchHint?.setHintTextColor(Color.parseColor("#999999"))
            etSearchHint?.setTextSize(TypedValue.COMPLEX_UNIT_PX, EAScreenUtils.dp2PxInt(context, 14f).toFloat())
            etSearchHint?.setPadding(PADDING_5, 0, PADDING_5, 0)
            if (!centerSearchEditable) {
                etSearchHint?.setCursorVisible(false)
                etSearchHint?.clearFocus()
                etSearchHint?.setFocusable(false)
                etSearchHint?.setOnClickListener(this)
            }
            etSearchHint?.setCursorVisible(false)
            etSearchHint?.setSingleLine(true)
            etSearchHint?.setEllipsize(TextUtils.TruncateAt.END)
            etSearchHint?.setImeOptions(EditorInfo.IME_ACTION_SEARCH)
            etSearchHint?.addTextChangedListener(centerSearchWatcher)
            etSearchHint?.setOnFocusChangeListener(focusChangeListener)
            etSearchHint?.setOnEditorActionListener(editorActionListener)
            etSearchHint?.setOnClickListener(OnClickListener { etSearchHint?.setCursorVisible(true) })
            val searchHintParams = RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            searchHintParams.addRule(RelativeLayout.END_OF, ivSearch?.id!!)
            searchHintParams.addRule(RelativeLayout.START_OF, ivVoice?.id!!)
            searchHintParams.addRule(RelativeLayout.CENTER_VERTICAL)
            searchHintParams.marginStart = PADDING_5
            searchHintParams.marginEnd = PADDING_5
            rlMainCenterSearch?.addView(etSearchHint, searchHintParams)

        } else if (centerType == TYPE_CENTER_CUSTOM_VIEW) {
            // 初始化中间自定义布局
            centerCustomView = LayoutInflater.from(context).inflate(centerCustomViewRes, rlMain, false)
            if (centerCustomView?.getId() == View.NO_ID) {
                centerCustomView?.setId(StatusBarUtils.generateViewId())
            }
            val centerCustomParams = RelativeLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT)
            centerCustomParams.marginStart = PADDING_12
            centerCustomParams.marginEnd = PADDING_12
            centerCustomParams.addRule(RelativeLayout.CENTER_IN_PARENT)
            //            if (leftType == TYPE_LEFT_TEXTVIEW) {
            //                centerCustomParams.addRule(RelativeLayout.END_OF, tvLeft.getId());
            //            } else if (leftType == TYPE_LEFT_IMAGEBUTTON) {
            //                centerCustomParams.addRule(RelativeLayout.END_OF, btnLeft.getId());
            //            } else if (leftType == TYPE_LEFT_CUSTOM_VIEW) {
            //                centerCustomParams.addRule(RelativeLayout.END_OF, viewCustomLeft.getId());
            //            }
            //            if (rightType == TYPE_RIGHT_TEXTVIEW) {
            //                centerCustomParams.addRule(RelativeLayout.START_OF, tvRight.getId());
            //            } else if (rightType == TYPE_RIGHT_IMAGEBUTTON) {
            //                centerCustomParams.addRule(RelativeLayout.START_OF, btnRight.getId());
            //            } else if (rightType == TYPE_RIGHT_CUSTOM_VIEW) {
            //                centerCustomParams.addRule(RelativeLayout.START_OF, viewCustomRight.getId());
            //            }
            rlMain?.addView(centerCustomView, centerCustomParams)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setUpImmersionTitleBar()
    }

    private fun setUpImmersionTitleBar() {
        val window = getWindow() ?: return
// 设置状态栏背景透明
        StatusBarUtils.transparentStatusBar(window)
        // 设置图标主题
        if (statusBarMode == 0) {
            StatusBarUtils.setDarkMode(window)
        } else {
            StatusBarUtils.setLightMode(window)
        }
    }

    private fun getWindow(): Window? {
        val context = context
        val activity: Activity?
        if (context is Activity) {
            activity = context
        } else {
            activity = (context as ContextWrapper).baseContext as Activity
        }
        return activity?.window
    }

    private val centerSearchWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable) {
            if (centerSearchRightType == TYPE_CENTER_SEARCH_RIGHT_VOICE) {
                if (TextUtils.isEmpty(s)) {
                    ivVoice?.setImageResource(R.drawable.comm_titlebar_voice)
                } else {
                    ivVoice?.setImageResource(R.drawable.comm_titlebar_delete_normal)
                }
            } else {
                if (TextUtils.isEmpty(s)) {
                    ivVoice?.setVisibility(View.GONE)
                } else {
                    ivVoice?.setVisibility(View.VISIBLE)
                }
            }
        }
    }

    private val focusChangeListener = OnFocusChangeListener { v, hasFocus ->
        if (centerSearchRightType == TYPE_CENTER_SEARCH_RIGHT_DELETE) {
            val input = etSearchHint?.getText().toString()
            if (hasFocus && !TextUtils.isEmpty(input)) {
                ivVoice?.setVisibility(View.VISIBLE)
            } else {
                ivVoice?.setVisibility(View.GONE)
            }
        }
    }

    private val editorActionListener = TextView.OnEditorActionListener { v, actionId, event ->
        if (listener != null && actionId == EditorInfo.IME_ACTION_SEARCH) {
            listener?.onClicked(v, ACTION_SEARCH_SUBMIT, etSearchHint?.getText().toString())
        }
        false
    }

    private var lastClickMillis: Long = 0     // 双击事件中，上次被点击时间

    override fun onClick(v: View) {
        if (listener == null) return

        if (v == llMainCenter && doubleClickListener != null) {
            val currentClickMillis = System.currentTimeMillis()
            if (currentClickMillis - lastClickMillis < 500) {
                doubleClickListener?.onClicked(v)
            }
            lastClickMillis = currentClickMillis
        } else if (v == tvLeft) {
            listener?.onClicked(v, ACTION_LEFT_TEXT, null)
        } else if (v == btnLeft) {
            listener?.onClicked(v, ACTION_LEFT_BUTTON, null)
        } else if (v == tvRight) {
            listener?.onClicked(v, ACTION_RIGHT_TEXT, null)
        } else if (v == btnRight) {
            listener?.onClicked(v, ACTION_RIGHT_BUTTON, null)
        } else if (v == etSearchHint || v == ivSearch) {
            listener?.onClicked(v, ACTION_SEARCH, null)
        } else if (v == ivVoice) {
            etSearchHint?.setText("")
            if (centerSearchRightType == TYPE_CENTER_SEARCH_RIGHT_VOICE) {
                // 语音按钮被点击
                listener?.onClicked(v, ACTION_SEARCH_VOICE, null)
            } else {
                listener?.onClicked(v, ACTION_SEARCH_DELETE, null)
            }
        } else if (v == tvCenter) {
            listener?.onClicked(v, ACTION_CENTER_TEXT, null)
        }
    }

    /**
     * 设置背景颜色
     *
     * @param color
     */
    override fun setBackgroundColor(color: Int) {
        if (viewStatusBarFill != null) {
            viewStatusBarFill?.setBackgroundColor(color)
        }
        rlMain?.setBackgroundColor(color)
    }

    /**
     * 设置背景图片
     *
     * @param resource
     */
    override fun setBackgroundResource(resource: Int) {
        setBackgroundColor(Color.TRANSPARENT)
        super.setBackgroundResource(resource)
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    fun setStatusBarColor(color: Int) {
        if (viewStatusBarFill != null) {
            viewStatusBarFill?.setBackgroundColor(color)
        }
    }

    /**
     * 是否填充状态栏
     *
     * @param show
     */
    fun showStatusBar(show: Boolean) {
        if (viewStatusBarFill != null) {
            viewStatusBarFill?.setVisibility(if (show) View.VISIBLE else View.GONE)
        }
    }

    /**
     * 切换状态栏模式
     */
    fun toggleStatusBarMode() {
        val window = getWindow() ?: return
        StatusBarUtils.transparentStatusBar(window)
        if (statusBarMode == 0) {
            statusBarMode = 1
            StatusBarUtils.setLightMode(window)
        } else {
            statusBarMode = 0
            StatusBarUtils.setDarkMode(window)
        }
    }

    /**
     * 获取标题栏底部横线
     *
     * @return
     */
    fun getButtomLine(): View ?{
        return if (null != viewBottomLine){
            viewBottomLine
        }else null
    }

    /**
     * 获取标题栏左边TextView，对应leftType = textView
     *
     * @return
     */
    fun getLeftTextView(): TextView ?{
        return if (null != tvLeft){
            tvLeft
        }else null
    }

    /**
     * 获取标题栏左边ImageButton，对应leftType = imageButton
     *
     * @return
     */
    fun getLeftImageButton(): ImageButton ?{
        return if (null != btnLeft){
            btnLeft
        }else null
    }

    /**
     * 获取标题栏右边TextView，对应rightType = textView
     *
     * @return
     */
    fun getRightTextView(): TextView ?{
        return if (null != tvRight){
            tvRight
        }else null
    }

    /**
     * 获取标题栏右边ImageButton，对应rightType = imageButton
     *
     * @return
     */
    fun getRightImageButton(): ImageButton ?{
        return if (null != btnRight){
            btnRight
        }else null
    }

    fun getCenterLayout(): LinearLayout ?{
        return if (null != llMainCenter){
            llMainCenter
        }else null
    }

    /**
     * 获取标题栏中间TextView，对应centerType = textView
     *
     * @return
     */
    fun getCenterTextView(): TextView ?{
        return if (null != tvCenter){
            tvCenter
        }else null
    }

    fun getCenterSubTextView(): TextView ?{
        return if (null != tvCenterSub){
            tvCenterSub
        }else null
    }

    /**
     * 获取搜索框布局，对应centerType = searchView
     *
     * @return
     */
    fun getCenterSearchView(): RelativeLayout ?{
        return if (null != rlMainCenterSearch){
            rlMainCenterSearch
        }else null
    }

    /**
     * 获取搜索框内部输入框，对应centerType = searchView
     *
     * @return
     */
    fun getCenterSearchEditText(): EditText ?{
        return if (null != etSearchHint){
            etSearchHint
        }else null
    }

    /**
     * 获取搜索框右边图标ImageView，对应centerType = searchView
     *
     * @return
     */
    fun getCenterSearchRightImageView(): ImageView ?{
        return if (null != ivVoice){
            ivVoice
        }else null
    }

    fun getCenterSearchLeftImageView(): ImageView ?{
        return if (null != ivSearch){
            ivSearch
        }else null
    }

    /**
     * 获取左边自定义布局
     *
     * @return
     */
    fun getLeftCustomView(): View ?{
        return if (null != viewCustomLeft){
            viewCustomLeft
        }else null
    }

    /**
     * 获取右边自定义布局
     *
     * @return
     */
    fun getRightCustomView(): View ?{
        return if (null != viewCustomRight){
            viewCustomRight
        }else null
    }

    /**
     * 获取中间自定义布局视图
     *
     * @return
     */
    fun getCenterCustomView(): View ?{
        return if (null != centerCustomView){
            centerCustomView
        }else null
    }

    /**
     * @param leftView
     */
    fun setLeftView(leftView: View) {
        if (leftView.id == View.NO_ID) {
            leftView.id = StatusBarUtils.generateViewId()
        }
        val leftInnerParams = RelativeLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT)
        leftInnerParams.addRule(RelativeLayout.ALIGN_PARENT_START)
        leftInnerParams.addRule(RelativeLayout.CENTER_VERTICAL)
        rlMain?.addView(leftView, leftInnerParams)
    }

    /**
     * @param centerView
     */
    fun setCenterView(centerView: View) {
        if (centerView.id == View.NO_ID) {
            centerView.id = StatusBarUtils.generateViewId()
        }
        val centerInnerParams = RelativeLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT)
        centerInnerParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        centerInnerParams.addRule(RelativeLayout.CENTER_VERTICAL)
        rlMain?.addView(centerView, centerInnerParams)
    }

    /**
     * @param rightView
     */
    fun setRightView(rightView: View) {
        if (rightView.id == View.NO_ID) {
            rightView.id = StatusBarUtils.generateViewId()
        }
        val rightInnerParams = RelativeLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT)
        rightInnerParams.addRule(RelativeLayout.ALIGN_PARENT_END)
        rightInnerParams.addRule(RelativeLayout.CENTER_VERTICAL)
        rlMain?.addView(rightView, rightInnerParams)
    }

    /**
     * 显示中间进度条
     */
    fun showCenterProgress() {
        progressCenter?.setVisibility(View.VISIBLE)
    }

    /**
     * 隐藏中间进度条
     */
    fun dismissCenterProgress() {
        progressCenter?.setVisibility(View.GONE)
    }

    /**
     * 显示或隐藏输入法,centerType="searchView"模式下有效
     *
     * @return
     */
    fun showSoftInputKeyboard(show: Boolean) {
        if (null != etSearchHint){
            if (centerSearchEditable && show) {
                etSearchHint?.setFocusable(true)
                etSearchHint?.setFocusableInTouchMode(true)
                etSearchHint?.requestFocus()
                EAScreenUtils.showSoftInputKeyBoard(context, etSearchHint!!)
            } else {
                EAScreenUtils.hideSoftInputKeyBoard(context, etSearchHint!!)
            }
        }
    }

    /**
     * 设置搜索框右边图标
     *
     * @param res
     */
    fun setSearchRightImageResource(res: Int) {
        if (ivVoice != null) {
            ivVoice?.setImageResource(res)
        }
    }

    /**
     * 获取SearchView输入结果
     */
    fun getSearchKey(): String {
        return if (etSearchHint != null) {
            etSearchHint?.getText().toString()
        } else ""
    }

    /**
     * 设置点击事件监听
     *
     * @param listener
     */

    fun setListener(listener: OnTitleBarListener) {
        this.listener = listener
    }

    fun setDoubleClickListener(doubleClickListener: OnTitleBarDoubleClickListener) {
        this.doubleClickListener = doubleClickListener
    }

    /**
     * 设置双击监听
     */


    val ACTION_LEFT_TEXT = 1        // 左边TextView被点击
    val ACTION_LEFT_BUTTON = 2      // 左边ImageBtn被点击
    val ACTION_RIGHT_TEXT = 3       // 右边TextView被点击
    val ACTION_RIGHT_BUTTON = 4     // 右边ImageBtn被点击
    val ACTION_SEARCH = 5           // 搜索框被点击,搜索框不可输入的状态下会被触发
    val ACTION_SEARCH_SUBMIT = 6    // 搜索框输入状态下,键盘提交触发
    val ACTION_SEARCH_VOICE = 7     // 语音按钮被点击
    val ACTION_SEARCH_DELETE = 8    // 搜索删除按钮被点击
    val ACTION_CENTER_TEXT = 9     // 中间文字点击

    /**
     * 点击事件
     */
    interface OnTitleBarListener : CommonTitleBar.OnTitleBarListener {
        /**
         * @param v
         * @param action 对应ACTION_XXX, 如ACTION_LEFT_TEXT
         * @param extra  中间为搜索框时,如果可输入,点击键盘的搜索按钮,会返回输入关键词
         */
        override fun onClicked(v: View, action: Int, extra: String?)
    }

    /**
     * 标题栏双击事件监听
     */
    interface OnTitleBarDoubleClickListener : CommonTitleBar.OnTitleBarDoubleClickListener {
        override fun onClicked(v: View)
    }


}