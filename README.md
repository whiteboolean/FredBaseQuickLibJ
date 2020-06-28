# 项目介绍
## 此项目为个人开发，使用Android MVVM架构+DataBinding +ViewModel + LiveData  + Room + ViewPager2 等最新前沿技术
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

## 项目中用到的第三方框架
### 感谢以下框架

* CC
>描述：实现组件化功能框架
>> [项目git地址](https://github.com/luckybilly/CC)
>> [文档说明](https://qibilly.com/CC-website/#/q&a)

* BaseQuickAdapter
> 描述：集成上拉刷新下拉加载框架
>> [项目git地址](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/readme/0-BaseRecyclerViewAdapterHelper.md)

* Xxpopup
> 描述：dialog+popoupwindow 合集(尚未接入)
>> [项目Git地址](https://github.com/whiteboolean/XPopup)

* AgentWeb
> 描述：Web组件
>> [项目Git地址](https://github.com/Justson/AgentWeb)

* BlankJUtils
> 描述：工具类大全
>> [项目Git地址](https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/README-CN.md)

* Lottile
> 描述:使用json文件完美实现各种动画
>> [项目Git地址](https://github.com/KingJA/LoadSir)

* Loadsir
> 描述:自动注入各种状态布局
>> [项目Git地址](https://github.com/KingJA/LoadSir)


## 第三方框架集合
名称 | 用途| 使用度
---|---|---|
BaseQuickAdapter|recyclerView适配器|20%|
CC|组件化|30%|
Xxpopup|对话框|0%|
AgentWeb|WebView|10%|
BlankJUtils|工具箱|10%|
Lottie|动画|20%|
Loadsir|页面状态|40%|






## License
 
     Copyright 2020 Fred Lei
 
     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at
 
         http://www.apache.org/licenses/LICENSE-2.0
 
     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.



