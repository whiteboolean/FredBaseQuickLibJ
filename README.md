# 此项目为个人开发，使用Android MVVM架构+DataBinding +Viewmodel + LiveData + Jetpack + Room + ViewPager2 等最新前沿技术
## 此项目为Java版本 ，此外kotlin版本名称为FredBaseQuickLibK ，项目尚未启动中

## 项目架构
* app： 为主功能模块，通过addComponent方式集成了其他的模块，但是不可以直接调用其他模块的类
* baseLib：为所有模块依赖组，主要为BaseActivity BaseFragment等一些基础框架
* baseNetWork：基础网络架构
* accountList：为项目中优惠活动列表模块
* news：为项目中新闻模块
* webView：为项目中webView模块

## 主要技术点
* 使用CC组件开发框架
* 项目汇总了许多优秀的第三方框架，避免了重复造轮子，并且进行了二次封装
* 此项目会迭代开发


## 第三方框架

* CC
>描述：
>> (项目git地址)[]

* BaseQuickAdapter
> 描述：集成上拉刷新下拉加载框架
>> [项目git地址](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/readme/0-BaseRecyclerViewAdapterHelper.md)

*
