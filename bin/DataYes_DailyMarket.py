# -*- coding:utf-8 -*-
"""
@author: wenhq
"""

import os
import tushare as ts
import tushare.util.dateu as dateu

from tushare.util.dateu import is_holiday

tradeyear = str(dateu.get_year());
tradeyear_path = os.path.join(os.getcwd(), 'DataYes',tradeyear)

# 按年创建每天行情数据文件
if not os.path.exists(tradeyear_path):
    os.makedirs(tradeyear_path)
# 保存文件
if not is_holiday(dateu.today()):
    df = ts.get_today_all();
    df.to_csv(os.path.join(tradeyear_path, ''.join([dateu.today().replace('-',''), '.csv'])), encoding='gb18030');
