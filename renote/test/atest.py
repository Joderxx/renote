# from renote.readfile import ReadFile
# from renote.algorithm import Algorithm
# import renote
#
# ids, filenames, texts, tags = ReadFile().load_data()
# algorithm = Algorithm(renote.path)
#
# algorithm.fit_countVectorizer(texts)
# algorithm.fit_classification(texts,tags)
# algorithm.fit_neighbor(texts,ids)
#
# print(algorithm.classification(texts[0]))
#
# print(algorithm.neighbor(texts[0]))


# from renote.readfile import ReadFile
# from renote.algorithm import Algorithm
# import renote
# from renote.cutfile import  CutFile
# c = CutFile()
# read = ReadFile()
# text = c.cut(read.read_data(''))
#
# algorithm = Algorithm(renote.path)
#
# print(algorithm.classification(text))
#
# print(algorithm.neighbor(text))

from renote.readfile import ReadFile

read = ReadFile()
sql = 'select * from my.text WHERE id = 100'
text = read.read_data(sql)
print(text)

