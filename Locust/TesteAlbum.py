# -*- coding: utf-8 -*-
from locust import HttpLocust, TaskSet, task
import random

class TesteAlbum(TaskSet):
    userID = random.randint(1,5001)
    @task
    def create(self):
        response = self.client.post("/albums", {"title": "Album de fotos do Bicampeonato do clube Atlético Mineiro","userId": str(self.userID)})
        self.client.delete("/albums/" + str(response.json()['id']))
      
class PoCLocust(HttpLocust):
    task_set = TesteAlbum
    host = "https://jsonplaceholder.typicode.com"
    sock = None
def __init__(self):
        super(PoCLocust, self).__init__()