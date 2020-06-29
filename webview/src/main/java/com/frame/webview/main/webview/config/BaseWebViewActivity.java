package com.frame.webview.main.webview.config;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.frame.webview.R;
import com.frame.webview.main.webview.config.web.WebLayout;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;


public class BaseWebViewActivity extends AppCompatActivity {



    protected AgentWeb mAgentWeb;
    private LinearLayout mLinearLayout;
    private Toolbar mToolbar;
    private TextView mTitleTextView;
    private AlertDialog mAlertDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_web_view);
        mLinearLayout = (LinearLayout) this.findViewById(R.id.container);
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("");
        mTitleTextView = (TextView) this.findViewById(R.id.toolbar_title);
        this.setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
            }
        });

        String html = getHtml();

        long p = System.currentTimeMillis();
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(new WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb().get();
//                .ready()
//                .go(getUrl());

        //跳转到网页地址
//        mAgentWeb.getUrlLoader().loadUrl(getUrl());

        //读取html数据
        mAgentWeb.getUrlLoader().loadData(html,"text/html", "UTF-8");

        long n = System.currentTimeMillis();
        Log.i("Info", "init used time:" + (n - p));


    }



    private com.just.agentweb.WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };
    private com.just.agentweb.WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mTitleTextView != null) {
                mTitleTextView.setText(title);
            }
        }
    };

    public String getUrl() {
        return "https://m.jd.com/";
    }


    private void showDialog() {

        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(this)
                    .setMessage("您确定要关闭该页面吗?")
                    .setNegativeButton("再逛逛", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                        }
                    })//
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                            BaseWebViewActivity.this.finish();
                        }
                    }).create();
        }
        mAlertDialog.show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("Info", "onResult:" + requestCode + " onResult:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }




    private String getHtml() {
        return "\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "\n" +
                "    <link rel=\"icon\" href=\"https://static.open-open.com/favicon.ico\">\n" +
                "    <title>EasyHTMLTags - jQuery - 组件类库 - 深度开源</title>\n" +
                "    <meta name=\"description\" content=\"EasyHTMLTags这个新的jQuery library能够帮忙你快速创建HTML标签。用法与Rails相似。\">\n" +
                "    <meta name=\"author\" content=\"深度开源\">\n" +
                "    <meta name=\"keywords\" content=\", jQuery\">\n" +
                "\n" +
                "  <link href=\"https://static.open-open.com/assets/fa/css/font-awesome.min.css?v=4.7.0\" rel=\"stylesheet\">\n" +
                "  <link href=\"https://static.open-open.com/css/bootstrap.min.css?v=1.0\" rel=\"stylesheet\">\n" +
                "  <link href=\"https://static.open-open.com/css/screen.css?v=1.0\" rel=\"stylesheet\">\n" +
                "\n" +
                "<link href=\"https://static.open-open.com/assets/highlight/atom-one-dark.css\" rel=\"stylesheet\">\n" +
                "  <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->\n" +
                "  <!--[if lt IE 9]>\n" +
                "    <script type=\"text/javascript\" src=\"https://static.open-open.com/js/ie8-responsive-file-warning.js\"></script><![endif]-->\n" +
                "  <script type=\"text/javascript\" src=\"https://static.open-open.com/js/ie-emulation-modes-warning.js\"></script>\n" +
                "\n" +
                "  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->\n" +
                "  <!--[if lt IE 9]>\n" +
                "    <script type=\"text/javascript\" src=\"https://static.open-open.com/js/html5shiv.min.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"https://static.open-open.com/js/respond.min.js\"></script>\n" +
                "    <![endif]-->\n" +
                "\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "\n" +
                "    <div class=\"wrapper bg-color-3\"><div class=\"Topnav \">\n" +
                "    <div class=container>\n" +
                "        <div class=\"row\">\n" +
                "            <div class=\"col-sm\">\n" +
                "            </div>\n" +
                "            <div class=\"col-sm text-center\">\n" +
                "                <a href=\"../\" class=\"navbar-brand top-logo\"><span></span></a>\n" +
                "            </div>\n" +
                "            <div class=\"col-sm\">\n" +
                "                   <div id=\"userNav\"  class=\"user float-right\">\n" +
                "                       <div class=\" \"> <a class=\"link-login\" href=\"javascript:void(null);\">登录</a>&nbsp;&nbsp;<a class=\"link-regist  btn-signup\" href=\"javascript:void(null);\">注册</a></div>\n" +
                "                  </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "<nav class=\"navbar navbar-expand-lg navbar-light ui-header\">\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"collapse navbar-collapse\" id=\"globalNavbar\">\n" +
                "            <div class=\"ui-search order-2\" id=\"topSearch\"></div>\n" +
                "            <ul class=\"navbar-nav mr-auto order-1\">\n" +
                "                <li class=\"nav-item \"><a class=\"nav-link\" href=\"/\">首页<span></span></a></li>\n" +
                "                <li class=\"nav-item active\"><a class=\"nav-link\" href=\"/project/\">项目<span></span></a></li>\n" +
                "                <li class=\"nav-item \"><a class=\"nav-link\" href=\"/lib/\">经验<span></span></a></li>\n" +
                "                <li class=\"nav-item \"><a class=\"nav-link\" href=\"/code/\">代码<span></span></a></li>\n" +
                "                <li class=\"nav-item \"><a class=\"nav-link\" href=\"/wenku/\">文库<span></span></a></li>\n" +
                "                <li class=\"nav-item \"><a class=\"nav-link\" href=\"/solution/\">问答<span></span></a></li>\n" +
                "                <li class=\"nav-item \"><a class=\"nav-link\" href=\"/blog/\">博客<span></span></a></li>\n" +
                "                <li class=\"nav-item \"><a class=\"nav-link\" href=\"/news/\">资讯<span></span></a></li>\n" +
                "            </ul>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</nav>    <div class=\"container breadcrum-nve\">\n" +
                "         <nav aria-label=\"breadcrumb\" class=\"clearfix\">\n" +
                "              <ol class=\"breadcrumb float-left\">\n" +
                "                <li class=\"breadcrumb-item\"><i aria-hidden=\"true\" class=\"fa fa-home\"></i> <a class=\"section\" href=\"/\">首页</a></li>\n" +
                "                <li class=\"breadcrumb-item \"><a class=\"section\" href=\"/project/zujian-leiku/\">组件类库</a> </li>\n" +
                "                <li class=\"breadcrumb-item active\" aria-current=\"page\">正文</li>\n" +
                "              </ol>\n" +
                "            </nav>\n" +
                "    </div>\n" +
                "\n" +
                "     <article id=\"readercontainer\">\n" +
                "        <div class=\"container\">\n" +
                "            <div class=\"ui-box border ut-pd20\">\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"col-md-7 border-right\">\n" +
                "                    <h1 class=\"headTit\"><span class=\"project-name\"></span><span class=\"project-title\">EasyHTMLTags</span><span class=\"float-right badge badge-dark project-version\">版本： </span></h1>\n" +
                "\n" +
                "                <div class=\"ui-tags\">\n" +
                "<a class=\"\" href=\"/project/tag/jquery.html\">jQuery</a> &nbsp;                </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"col-md-5\">\n" +
                "                        <div class=\"row\">\n" +
                "                            <div class=\"col-md-8\">\n" +
                "                                <ul class=\"ui-list project-info\">\n" +
                "                                    <li><label>授权协议:</label><span> </span></li>\n" +
                "                                    <li><label>开发语言:</label><span> JavaScript&nbsp;&nbsp;</span></li>\n" +
                "                                    <li><label>操作系统:</label><span> </span></li>\n" +
                "                                </ul>\n" +
                "                            </div>\n" +
                "                            <div class=\"col-md-4\">\n" +
                "                                <a href=\"http://blog.flvorful.com/articles/2010/10/10/easyhtmltags-a-new-jquery-library-to-create-html-tags-easily\" class=\"btn btn-outline-secondary btn-sm btn-block\">项目首页</a>\n" +
                "                                <a href=\"\" class=\"btn btn-outline-secondary btn-sm btn-block ut-mt10\">项目文档</a>\n" +
                "                                <a href=\"http://github.com/flvorful/jQuery-Plugins/raw/master/easy_html/jquery.easy_html_tags.js\" class=\"btn btn-outline-secondary btn-sm btn-block ut-mt10\">项目下载</a>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"side-widget sticky-top\">\n" +
                "                <div class=\"vote post-left ut-pt20\">\n" +
                "                    <i class=\" iconfont toggle-supports oo-vote-up  \" data-type=\"project\" data-num=\"0\" data-id=\"4632\" data-enable=\"\" title=\"推荐\"><span class=\"vote-count\" data-num=\"0\">0</span></i>\n" +
                "                   <button type=\"button\" id=\"favorites\" data-type=\"project\" data-id=\"4632\" data-title=\"EasyHTMLTags\" class=\"toggle-favorites btn btn-outline-secondary  \" title=\"收藏\"><i class=\"fa fa-bookmark-o\" aria-hidden=\"true\"></i></button>\n" +
                "                    <div class=\"bdsharebuttonbox ut-mt10\">\n" +
                "                    <a href=\"#\" class=\"bds_qzone\" data-cmd=\"qzone\" title=\"分享到QQ空间\"></a>\n" +
                "                    <a href=\"#\" class=\"bds_tsina\" data-cmd=\"tsina\" title=\"分享到新浪微博\"></a>\n" +
                "                    <a href=\"#\" class=\"bds_tqq\" data-cmd=\"tqq\" title=\"分享到腾讯微博\"></a>\n" +
                "                    <a href=\"#\" class=\"bds_weixin\" data-cmd=\"weixin\" title=\"分享到微信\"></a>\n" +
                "                </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"row ut-mt20\">\n" +
                "                <div class=\"col-md-9 article-body border-right\">\n" +
                "                    <div data-spy=\"scroll\" data-target=\"#article-navbar\" data-offset=\"0\">\n" +
                "<p>EasyHTMLTags这个新的jQuery library能够帮忙你快速创建HTML标签。用法与Rails相似。<BR>&lt;IMG border=0 alt=EasyHTMLTags.jpg src=\"<a href=\"https://simg.open-open.com/show/b4901ab1c2f4a47f9e69807046caafba.jpg\">https://simg.open-open.com/show/b4901ab1c2f4a47f9e69807046caafba.jpg</a>\" alignment=\"\"&gt;</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"post-copyright ut-mt20\">\n" +
                "                        <div><i class=\"fa fa-exclamation-triangle\" aria-hidden=\"true\"></i>&nbsp;本文由用户<a title=\"  成siuibiiik \" target=\"_blank\" href=\"https://user.open-open.com/u/1\" class=\"color-orange\"> 码头工人</a>自行上传分享，仅供网友学习交流。所有权归原作者，若您的权利被侵害，请联系管理员。</div>\n" +
                "                        <div><i class=\"fa fa-exclamation-triangle\" aria-hidden=\"true\"></i>&nbsp;转载本站原创文章，请注明出处，并保留原始链接、图片水印。</div>\n" +
                "                        <div><i class=\"fa fa-exclamation-triangle\" aria-hidden=\"true\"></i>&nbsp;本站是一个以用户分享为主的开源技术平台，欢迎各类分享！</div>\n" +
                "                        <div><i class=\"fa fa-link\" aria-hidden=\"true\"></i>&nbsp;本文地址：<a href=\"https://www.open-open.com/project/5068361469160995238.html\" target=\"_blank\">https://www.open-open.com/project/5068361469160995238.html</a></div>\n" +
                "                        <div><span class=\"ui-tags\"> <a class=\"label label-info\" href=\"/project/tag/jquery.html\">jQuery</a></span></div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"col-md-3\">\n" +
                "                    <div class=\"ui-box border ut-pd10\">\n" +
                "                        <div class=\"title clearfix\">\n" +
                "                            <h3>相关项目</h3>\n" +
                "\n" +
                "                        </div>\n" +
                "                        <ul class=\"ui-list\">\n" +
                "                            <li><span class=\"dot\"></span><a class=\"ellipsis\" href=\"/project/5068361469160995238.html\">&nbsp;EasyHTMLTags</a></li>\n" +
                "                        </ul>\n" +
                "                    </div>\n" +
                "\n" +
                "                    <div class=\"ui-box border ut-pd10 ut-mt20\" >\n" +
                "                        <div class=\"title clearfix\"><h3>相关经验</h3></div>\n" +
                "                        <ul class=\"ui-list\">\n" +
                "                        </ul>\n" +
                "                    </div>\n" +
                "                    <div class=\"ui-box border ut-pd10 ut-mt20\" >\n" +
                "                        <div class=\"title clearfix\"><h3>相关资讯</h3></div>\n" +
                "                        <ul class=\"ui-list\">\n" +
                "                        </ul>\n" +
                "                    </div>\n" +
                "                    <div class=\"ui-box border ut-pd10 ut-mt20\" >\n" +
                "                        <div class=\"title clearfix\"><h3>相关文档</h3></div>\n" +
                "                        <ul class=\"ui-list\">\n" +
                "                        </ul>\n" +
                "                    </div>\n" +
                "                    <div class=\"sticky-top hidden-xs ut-mt20 hidden-sm \" id=\"article-navbar\" role=\"complementary\"><div class=\"title\"><h3>目录</h3></div></div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "\n" +
                "        </div>\n" +
                "\n" +
                "      </article>\n" +
                "\n" +
                "\n" +
                "</div>\n" +
                "<footer >\n" +
                "    <div class=\"container py-5\">\n" +
                "      <div class=\"row\">\n" +
                "        <div class=\"col-md-3\">\n" +
                "          <h5>社区</h5>\n" +
                "            <div class=\"row\"><div class=\"col-md-6\"><a class=\"text-muted\" href=\"/project/\">项目</a></div><div class=\"col-md-6\"><a class=\"text-muted\" href=\"/solution/\">问答</a></div><div class=\"col-md-6\"><a class=\"text-muted\" href=\"/wenku/\">文库</a></div><div class=\"col-md-6\"><a class=\"text-muted\" href=\"/code/\">代码</a></div><div class=\"col-md-6\"><a class=\"text-muted\" href=\"/lib/\">经验</a></div><div class=\"col-md-6\"><a class=\"text-muted\" href=\"/news/\">资讯</a></div></div>\n" +
                "          <ul class=\"list-unstyled text-small ut-mt20\"><li><a class=\"text-muted\" title=\" 安卓开发专栏\" target=\"_blank\" href=\"http://www.open-open.com/lib/list/177\">安卓开发专栏</a></li><li><a class=\"text-muted\" href=\"http://www.open-open.com/lib/tag/开发者周刊\" target=\"_blank\" rel=\"tag\">开发者周刊</a></li><li><a class=\"text-muted\" href=\"http://www.open-open.com/lib/view/open1475497562965.html\" target=\"_blank\" rel=\"tag\">Android Studio 使用推荐</a></li><li><a class=\"text-muted\" href=\"http://www.open-open.com/lib/view/open1475497355674.html\" target=\"_blank\" rel=\"tag\">Android开发推荐</a></li></ul>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"col-md-3\">\n" +
                "          <h5>帮助中心</h5>\n" +
                "          <ul class=\"list-unstyled text-small\"><li><a class=\"text-muted\" href=\"/upload.html\">文档上传须知</a></li></ul>\n" +
                "          <h5>关于我们</h5>\n" +
                "          <ul class=\"list-unstyled text-small\"><li><a class=\"text-muted\" href=\"/about.html\">关于深度开源</a></li><li><a class=\"text-muted\" href=\"/duty.html\">免责声明</a></li><li><a class=\"text-muted\" href=\"/contact.html\">联系我们</a></li></ul>\n" +
                "        </div>\n" +
                "        <div class=\"col-md-6 text-center\"><img class=center-block src=\"https://static.open-open.com/img/logo01.svg\" width=190px alt=\"深度开源\"><small class=\"d-block mb-3 text-muted ut-mt40\">&copy; 2006-2019 深度开源 —— 开源项目,开源代码,开源文档,开源新闻,开源社区&nbsp;&nbsp;杭州精创信息技术有限公司&nbsp;&nbsp;<br/><br/><img src=\"https://static.open-open.com/img/beian.png\"/><a target=\"_blank\" href=\"http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=33010602002439\">&nbsp;&nbsp;浙公网安备 33010602002439号</a>&nbsp;&nbsp;<a target=\"_blank\" href=\"http://www.beian.miit.gov.cn/\">浙ICP备09019653号-31</a></small></div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "</footer>\n" +
                "\n" +
                "<div id=\"fTools\"><span id=\"gotop\"> <i class=\"fa fa-arrow-up\" aria-hidden=\"true\"></i> </span><span id=\"feedback\" title=\"建议反馈\"> <i class=\"fa fa-inbox\" aria-hidden=\"true\"></i></span></div>\n" +
                "    <!-- Bootstrap core JavaScript\n" +
                "    ================================================== -->\n" +
                "    <!-- Placed at the end of the document so the pages load faster -->\n" +
                "    <script type=\"text/javascript\" src=\"https://static.open-open.com/js/lib.js\"></script>\n" +
                "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js\"></script>\n" +
                "    <script src=\"https://static.open-open.com/js/bootstrap.min.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"https://static.open-open.com/js/base.js?v=1.01\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"https://static.open-open.com/js/jq-plug.js?v=1.01\"></script>\n" +
                " <script>\n" +
                "    $(function () {\n" +
                "        JC.reminderPop();//弹出用户信息\n" +
                "        $(\"#topSearch\").searchInit();\n" +
                "        $(\".link-login\").click(function(){ JC.lORr('login'); });\n" +
                "        //用户登录状态\n" +
                "            JC.setLogin(false);\n" +
                "    });\n" +
                "\n" +
                "  </script>\n" +
                "\n" +
                "    <!-- JavaScript at the bottom for fast page loading -->\n" +
                "    <script type=\"text/javascript\">\n" +
                "        var ImageUploadUrl =\"https://simg.open-open.com\";\n" +
                "    </script>\n" +
                "\n" +
                "    <script type=\"text/javascript\" src=\"https://static.open-open.com/assets/ckeditor/ckeditor.js\" charset=\"UTF-8\"></script>\n" +
                "    <script src=\"https://static.open-open.com/assets/highlight/highlight.js\"></script>\n" +
                "\n" +
                "    <script type=\"text/javascript\">\n" +
                "    JC.init(\"\",\"4632\",\"project\",5);\n" +
                "    var slug = \"5068361469160995238\";\n" +
                "    var title = \"EasyHTMLTags\";\n" +
                "    var username = \"\";\n" +
                "    var useremail = \"\";\n" +
                "    var summary = \"EasyHTMLTags这个新的jQuery library能够帮忙你快速创建HTML标签。用法与Rails相似。\";\n" +
                "    var num_page = \"1\";\n" +
                "    var tagList = [{id:37,name:\"jQuery\"} ];\n" +
                "    var category_name = \"\";\n" +
                "    var jc = {},fav=[];\n" +
                "    var is_push=\"True\";\n" +
                "    JC.setPush(is_push=='True'?true:false);\n" +
                "    $(function () {\n" +
                "        $('#article-navbar').contentIndex();//文章页目录索引\n" +
                "        hljs.initHighlightingOnLoad();\n" +
                "        });\n" +
                "\n" +
                "    </script>\n" +
                "    <script src=\"https://static.open-open.com/assets/clipboard.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"https://static.open-open.com/js/show.js?v=1.01\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"https://static.open-open.com/js/jq-plug.js?v=1.01\"></script>\n" +
                "    <!-- end scripts -->\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
    }

}
