package cn.lag.lag.model

import cn.lag.lag.modelImpl.IFootModel

/**
 * 项目名称：Android-Test
 * 类名：cn.lag.lag.model
 * 创建人：Heaven.li
 * 创建时间：2019-10-15 16:15
 * 备注：模块管理，分块处理逻辑
 */
class ModelManager {

    private var foot: FootBarModel? = null

    companion object {
        private var instance: ModelManager? = null
            get() {
                if (field == null) {
                    field = ModelManager()
                }
                return field
            }

        fun get(): ModelManager {
            return instance!!
        }
    }

    fun getFootBar(): IFootModel {
        if (null == this.foot) {
            foot = FootBarModel()
        }
        return foot as FootBarModel
    }
}