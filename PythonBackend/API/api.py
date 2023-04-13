from fastapi import FastAPI,Request
from fastapi.responses import JSONResponse
from pydantic import BaseModel
from fastapi.middleware.trustedhost import TrustedHostMiddleware
from fastapi.middleware.cors import CORSMiddleware
import uvicorn
import pymongo
client = pymongo.MongoClient("mongodb://mongo-root:passw0rd@35.232.127.119:27017/?authMechanism=DEFAULT&authSource=word_game")
db=client['word_game']
collection=db['words']

app = FastAPI()
origins = ["*"] # Tüm isteklere izin vermek için "*" kullanıyoruz

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

class wordinfo(BaseModel):
    word: str



@app.post("/word_check")
async def word_check(request: Request):
    req_json= await request.json()
    word=req_json['word']
    print(word)
    
    if word!= None:
        query={'word':word.lower()}
        result=collection.find(query)
        records=list(result)
        print(records)
        if(len(records)!=0):
            result = {            
            "message":"Word found!",
            "success": True
            }
            return JSONResponse(content=result ,status_code=200)
            
        result = {            
            "message":"Word not found!",
            "success": False
        }
        return JSONResponse(content=result ,status_code=500)
        
        

@app.get("/healthcheck")
async def connection_checkpoint():
    try:
        client.admin.command('ping')
        result = {            
            "message":"MongoDB connection is a successfuly!",
            "success": True
        }
        return JSONResponse(content=result ,status_code=200)
    except:
        result = {            
            "message":"MongoDB connection is not successfuly!",
            "success": True
        }
        return JSONResponse(content=result ,status_code=500)




if __name__ == '__main__':
  
    uvicorn.run(app,host="0.0.0.0",port=8000)