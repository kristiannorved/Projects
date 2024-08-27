from recommenders import popularity_recommender_py, item_similarity_recommender_py
import pandas as pd
import os
import sys

os.chdir(sys.path[0]) # set working directory to file location

# constants
N = 10000 # number of rows to take from the original dataset

# import data from local machine
metadata = pd.read_csv('metadata.csv')
triplet = pd.read_csv('triplet.csv', low_memory=False)
triplet.columns = ['user_id', 'song_id', 'listen_count']
df = pd.merge(triplet, metadata.drop_duplicates(['song_id']), on='song_id', how='left')
df = df.iloc[:N] # select first N rows of dataset 

# data transformation
df['song'] = df[['artist_name', 'release']].agg(' - '.join, axis=1)
df = df.drop('artist_name', axis=1)
df = df.drop('release', axis=1)
df_grouped = df.groupby(['song']).agg({'listen_count': 'count'}).reset_index()
grouped_sum = df_grouped['listen_count'].sum()
df_grouped['percentage'] = df_grouped['listen_count'].div(grouped_sum)*100
df_grouped = df_grouped.sort_values('listen_count', ascending=False)
df_grouped = pd.DataFrame(df_grouped)
print(df_grouped.head())
print('Dataset shape:', df_grouped.shape)
users = df['user_id'].unique()
print('Number of unique users:', len(users))
songs = df['song'].unique()
print('Number of unique songs:', len(songs))

# establish model
pm = popularity_recommender_py()
pm.create(df, 'user_id', 'song')
model = item_similarity_recommender_py()
model.create(df, 'user_id', 'song')

# print the songs for the user in training data
user_id = users[7]
user_items = model.get_user_items(user_id)
print("------------------------------------------------------------------------------------")
print("Training data songs for the user userid: %s:" % user_id)
print("------------------------------------------------------------------------------------")
for user_item in user_items:
    print(user_item)

# print recommended songs for the user
print("------------------------------------------------------------------------------------")
print("Recommended songs for the user userid: %s:" % user_id)
print("------------------------------------------------------------------------------------")
print(model.recommend(user_id))

# print similar songs to a given song
print("------------------------------------------------------------------------------------")
print("Generating similar songs to \"Florence + The Machine - Lungs\"")
print("------------------------------------------------------------------------------------")
print(model.get_similar_items(['Florence + The Machine - Lungs']))