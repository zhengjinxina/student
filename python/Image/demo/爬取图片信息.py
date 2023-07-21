import requests
from datetime import datetime
from bs4 import BeautifulSoup
import sched
import time

s = sched.scheduler(time.time, time.sleep)


def download(url):
    res =requests.get(url)
    res.encoding="utf-8"
    img = res.content
    now = datetime.now()
    timestamp = datetime.timestamp(now)
    fileName = 'img/' + str(timestamp).split('.')[1] + '.jpg'
    print(fileName)
    with open(fileName,"wb") as f:
        f.write(img)
        f.close()
        print("保存成功！")
def get_html():
    url = "https://www.163.com/"
    headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36'}
    source = requests.get(url, headers=headers)
    source.encoding="utf-8"
    soup = BeautifulSoup(source.text,"html.parser")
    pic_list = soup.find_all("img")
    for item in pic_list:
        if item.get('src') is None:
            continue
        else:
            if find_substring_in(item.get('src'),"http"):
                url = item.get('src')
                download(url)
            else:
                url = "https:"+item.get('src')
                download(url)

    for item in  pic_list:
        if item.get('data-original') is None:
            continue
        else:
            if find_substring_in(item.get('data-original'),"http"):
                url = item.get('data-original')
                download(url)
            else:
                url = "https:"+item.get('data-original')
                download(url)

def find_substring_in(s, sub):
    """
    使用in关键字查找子字符串
    """
    if sub in s:
        return True
    else:
        return False

def perform(inc):
    get_html()
    s.enter(inc, 0, perform, (inc,))


def main():
    inc = 300  # 调用间隔设定为300秒即5分钟。
    s.enter(inc, 0, perform, (inc,))
    s.run()

if __name__ == '__main__':
    main()

