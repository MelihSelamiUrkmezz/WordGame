import tdk.gts
import re
import nltk
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
turkish_stop_words = stopwords.words('turkish')

words=set()

def get_turkish_word():

    for word in tdk.gts.index():
        # Özel karakterleri temizleme(Noktalama işaretleri özel karakterler)
        word=re.sub(r'[^\w\s]', '',word)
        # Stop word olup olmadığını kontrol etme(Anlamsız kelimeler çıkarılır)
        if word not in turkish_stop_words:
            word_preprocessing(word)


def word_preprocessing(word):

    word_list=word.split(' ')

    if(len(word_list)>1):
        for mini_word in word_list:
            if(len(mini_word)>=3):
                words.add(mini_word)
                print(mini_word)
    else:
        if(len(word)>=3):
            words.add(word)
            print(word)

get_turkish_word()

print("Wordset's length:"+str(len(words)))


