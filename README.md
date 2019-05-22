# build-platform
基础项目打包工程

# clean
如果执行 `mvn clean` 提示 `.rpm` 文件被占用的话

windows 可以关闭 `java.exe` 即可
```
taskkill /f /im java.exe
```

linux 同理
```
ps -ef | grep java | grep -v grep | awk '{print $2}' | xargs -9 kill
```
# package
```
mvn clean package -DskipTests
```
