下面我们通过一个简单的示例，来看看Retrofit结合Kotlin协程请求网络是怎么开发的。

### 需求分析

#### 第一步，产品需求

首先，产品小姐姐给到我们的需求是这样子的：

1. 点击按钮，先请求每日一词接口，获取每日一词
2. 点击按钮，请求翻译接口，将每日一词翻译

#### 第二步，接口定义

因此这个需求我们需要有两个接口：

1. 每日一词接口
2. 翻译接口

具体的接口定义就不写了

#### 第三步，UI设计

具体的布局如下：

![UI](https://upload-images.jianshu.io/upload_images/2570030-f1cd43e948f2ae91.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

布局文件如下：

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <Button
        android:id="@+id/btnDailyWord"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="请求每日一词接口" />

    <TextView
        android:id="@+id/tvDailyWord"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="每日一词" />

    <Button
        android:id="@+id/btnTranslate"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="请求翻译接口" />

    <TextView
        android:id="@+id/tvTranslate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="翻译结果" />

</LinearLayout>
```

### 预备工作

经过评估，我们使用最新版的Retrofit2.9.0，最新版原生支持协程，不需要额外依赖其他Adapter库：

```groovy
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation "com.squareup.okhttp3:logging-interceptor:4.9.0"
```

详细的依赖可以参考附录给出的完整示例。

### 准备API接口

由于最新版的Retrofit2.9.0原生支持协程，接口定义直接写成挂起函数就可以了，返回类型直接写成网络数据返回类型即可。
然后我们在companion object域里面创建接口实现类：

```kotlin
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslateService {

    /**
     * 获取每日一词接口
     * 新版本的Retrofit支持直接声明成挂起函数，并且函数直接返回网络返回数据
     */
    @GET("dailyword")
    suspend fun requestDailyWord(): BaseResult<String>

    /**
     * 翻译接口
     */
    @GET("translate")
    suspend fun requestTranslateResult(@Query("input") input: String): BaseResult<String>

    companion object {

        private const val BASE_URL = "http://172.16.47.80:8080/TestServer/"
        private var service: TranslateService? = null

        /**
         * 通过Retrofit的动态代理生成TranslateService实现类
         */
        fun getApi(): TranslateService {
            if (null == service) {
                val httpLoggingInterceptor =
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

                val client = OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                service = retrofit.create(TranslateService::class.java)
            }

            return service!!
        }
    }

}
```

其中，返回数据类定义如下：

### 网络返回数据类

```kotlin
/**
 * 网络返回数据基类
 */
data class BaseResult<T>(val code: String, val msg: String, val data: T)
```

### 实现ViewModel

创建ViewModel，主要功能是详情Activity的操作，输入数据，请求网络，返回数据。
都有详细注释，直接看代码：

```kotlin
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class TranslateViewModel : ViewModel() {

    /**
     * 每日一词LiveData
     */
    val dailyWordLiveData: MutableLiveData<Result<BaseResult<String>>> = MutableLiveData()

    /**
     * 最简单的无任何输入的请求
     * 通过扩展属性viewModelScope的launch函数开启协程访问网络并且返回
     */
    fun requestDailyWord() {
        viewModelScope.launch {
            val result = try {
                // 网络返回成功
                Result.success(TranslateService.getApi().requestDailyWord())
            } catch (e: Exception) {
                // 网络返回失败
                Result.failure(e)
            }
            // 发射数据，之后观察者就会收到数据
            // 注意这里是主线程，直接用setValue()即可
            dailyWordLiveData.value = result
        }
    }

    /**
     * 翻译输入LiveData
     */
    private val inputLiveData: MutableLiveData<String> = MutableLiveData()

    /**
     * 翻译结果输出LiveData
     * 通过LiveData的扩展函数switchMap()实现变换，在下游能够返回支持协程的CoroutineLiveData
     * CoroutineLiveData是通过Top-Level函数里面的liveData()方法来创建，在这里可以传入闭包，开启协程访问网络并且返回
     *
     * 注:
     * 1. LiveDataScope, ViewModelScope和lifecycleScope会自动处理自身的生命周期，在生命周期结束时会自动取消没有执行完成的协程任务
     * 2. 其中map和switchMap与RxJava中的map和flatMap有点类似
     */
    val translateResult: LiveData<Result<BaseResult<String>>> = inputLiveData.switchMap { input ->
        liveData {
            val result = try {
                // 网络返回成功
                Result.success(TranslateService.getApi().requestTranslateResult(input))
            } catch (e: Exception) {
                // 网络返回失败
                Result.failure(e)
            }
            // 发射数据，之后观察者就会收到数据
            emit(result)
        }
    }

    /**
     * 开始翻译
     */
    fun requestTranslate(input: String) {
        inputLiveData.value = input
    }

}
```

### 实现Activity

创建Activity，主要功能是观察ViewModel的数据返回并展示，响应用户的点击行为，通知ViewModel去请求网络：

```kotlin
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nan.jetpackprimer.R
/**
 * 导入自动生成的视图注入类，在代码中可以直接使用控件
 */
import kotlinx.android.synthetic.main.activity_translate.*

/**
 * LiveData结合协程
 */
class TranslateActivity : AppCompatActivity() {

    /**
     * 通过ComponentActivity的扩展函数viewModels()方便获取ViewModel
     */
    private val viewModel: TranslateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)

        /**
         * 观察每日一词结果
         */
        viewModel.dailyWordLiveData.observe(this) { result ->
            val dailyWordResult = result.getOrNull()
            if (null == dailyWordResult) {
                tvDailyWord.text = "获取失败"
                return@observe
            }

            tvDailyWord.text = dailyWordResult.data
        }

        /**
         * 观察翻译结果
         */
        viewModel.translateResult.observe(this) { result ->
            val translateResult = result.getOrNull()
            if (null == translateResult) {
                tvTranslate.text = "翻译失败"
                return@observe
            }

            tvTranslate.text = translateResult.data
        }

        /**
         * 按钮点击监听
         * 获取每日一词
         */
        btnDailyWord.setOnClickListener {
            viewModel.requestDailyWord()
        }

        /**
         * 按钮点击监听
         * 获取EditText输入并且通知ViewModel开始翻译
         */
        btnTranslate.setOnClickListener {
            val input = tvDailyWord.text.toString().trim()
            viewModel.requestTranslate(input)
        }
    }

}
```

### 服务端部分代码

每日一词接口：

```java
@WebServlet("/dailyword")
public class DailyWordServlet extends BaseJsonServlet {
    @Override
    protected ResponseEntity onHandle(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = ResponseCode.OK;
        responseEntity.msg = "成功";
        responseEntity.data = "每天都是好心情";
        return responseEntity;
    }
}
```

翻译接口：

```java
@WebServlet("/translate")
public class TranslateServlet extends BaseJsonServlet {
    @Override
    protected ResponseEntity onHandle(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String input = req.getParameter("input");
        String translateResult = input + " -> " + "Good mood every day";

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.code = ResponseCode.OK;
        responseEntity.msg = "Good mood every day";
        responseEntity.data = translateResult;

        return responseEntity;
    }
}
```

详情可以参考附录给出的完整示例。

### 思考

通过这两个例子：

1. 我们掌握了在ViewModel中最简单的如何开启协程访问网络（无参数的形式），以及如何响应UI层的输入然后开启协程访问网络最终又把返回发送给UI层（有参数的形式）
2. 我们掌握了如何利用JetPack的ViewModel、LiveData、KTX等组件搭建项目架构
2. 这个例子暂时不能体现使用协程的优势，后面读者可以自己尝试增加一些诸如链式请求、请求合并、异步处理请求结果等功能，通过同步的方式去写异步的代码，感受一下协程的强大。另外也可以用RxJava实现一遍，对比一下。

### 附录

最后，附上完整代码地址：

客户端： 
https://github.com/huannan/JetpackPrimer/tree/master/app/src/main/java/com/nan/jetpackprimer/livedata/simple4

服务端： 
https://github.com/huannan/Architecture/tree/master/day31_okhttp/TestServer