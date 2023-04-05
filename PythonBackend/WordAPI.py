import tdk.gts
import re
import nltk
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
from pymongo import MongoClient
import os
from os.path import join, dirname
from dotenv import load_dotenv

#.env file configration
dotenv_path = join(dirname(__file__), '.env')
load_dotenv(dotenv_path)

#Database Configration
CONNECTION_STRING = os.environ.get('CONNECTION_STRING')
client = MongoClient(CONNECTION_STRING)
db=client['word_game']
collection=db['words']

turkish_stop_words = stopwords.words('turkish')

words_dict=[]
words=set()

def get_turkish_word():

    for word in tdk.gts.index():
        word=re.sub(r'[^\w\s]', '',word)
        if word not in turkish_stop_words:
            word_preprocessing(word)


def word_preprocessing(word):

    word_list=word.split(' ')

    if(len(word_list)>1):
        for mini_word in word_list:
            if(len(mini_word)>=3):
                words.add(mini_word)
    else:
        if(len(word)>=3):
            words.add(word)       

def convert_json_write_db(words):
    
    for word in words:
        rec={
            'word': word,
            'quickKey': word[0]
        }
        words_dict.append(rec)
    
    collection.insert_many(words_dict)
                

get_turkish_word()
convert_json_write_db(words=words)

print("Wordset's length:"+str(len(words)))


