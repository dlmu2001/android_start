# android_start
Something help to start an commercial android app  quickly

#  Fabric Crashlytics for crash statistics
Fabric增加App需要使用插件，使用官方教程添加app,并进行验证

因为要使用插件，在总工程的buildscript里面添加(增加
完app后，后面可以删除这段)
        
        buildscript {
          repositories {
            
            maven { url 'https://maven.fabric.io/public' }
        }
        dependencies {
            
            classpath 'io.fabric.tools:gradle:1.+' 
            
         }
       }
       
在总工程的allprojects添加
        
        allprojects {
            repositories {            
              maven { url 'https://maven.fabric.io/public' }            
          }
        }

一般在app的manifest添加网络权限，并设置apikey
        
        <uses-permission android:name="android.permission.INTERNET" />
        <meta-data
            android:name="io.fabric.ApiKey"
            android:value="55d622e662c8b*****e3f5acd18e3d9ef24"
            />
在程序启动的时候初始化
        
        CrashlyticsHelper.init(this);
        
这样处理以后，Fatal的Exception就会自动上报

对于Catch的Exception,对应于Crashlytics的Non-Fatal，可以使用
        
        CrashlyticsHelper.logException(Exception e);
需要注意，这个Api只对release版本有效，而且Crashlytics有最多保存６条Non-Fatal的限制。
