1. 编写一个shell脚本(linux)，功能如下
  在给定文件中搜索指定内容，并将搜索结果(含内容出现的行号)保存到新的文件中，同时结果输出到控制台
  :对应文件-->search.sh

  实例：
  在控制台输入命令-->  bash search.sh "disk" "./systemConditions.sh"
  返回结果--> 7:#disk使用阈值 8:disk_warn='90' 23:#---disk 24:item_disk () { 25:disk_use=`df -P | grep /dev | grep -v -E '(tmp|boot)' | awk '{print $5}' | cut -f 1 -d "%"` 26:echo "$now 当前磁盘使用率为 ${disk_use}%" 32:item_disk
  存储文件在"当前"文件夹下，名字为-->temp.log

2. 编写一个shell: 显示当前硬盘使用情况，内存使用情况，CPU使用情况
  :对应文件-->systemConditions.sh
  实例：
    在控制台输入命令-->bash systemConditions.sh
    返回结果-->2023-07-19 04:04:50 当前cpu使用率为 7%
           2023-07-19 04:04:50 当前内存剩余空间为 1352MB
           2023-07-19 04:04:50 当前磁盘使用率为 28%