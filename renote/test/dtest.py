import numpy as np
import re

# dr = re.compile(r"class='.+'|id='.+'",re.S)
#
# s = '''<div class='blogpost-body' id='cnblogs_post_body' aaaaa>'''
#
# print(dr.sub('',s))

import jieba
s = "value='${driverClass}'"
w = jieba.cut(s)
print(' '.join(w))