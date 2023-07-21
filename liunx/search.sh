#!/bin/bash
str=$1
filename=$2
echo `grep -n $str $filename`  >> ./temp.log
echo `grep -n $str $filename`