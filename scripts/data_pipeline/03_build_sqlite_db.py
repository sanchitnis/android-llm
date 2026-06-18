import os
import json
import sqlite3

def build_db():
    print("Compiling enriched admission.db...")
    
    if not os.path.exists("data/raw_courses.json"):
        print("ERROR: raw_courses.json not found. Run previous steps first.")
        return

    db_path = "data/admission.db"
    if os.path.exists(db_path):
        os.remove(db_path)
        
    conn = sqlite3.connect(db_path)
    cursor = conn.cursor()
    
    cursor.execute('''
        CREATE TABLE courses (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT,
            duration TEXT,
            fee TEXT,
            deadline TEXT,
            eligibility TEXT,
            highlights TEXT,
            why_join TEXT
        )
    ''')
    
    cursor.execute('''
        CREATE VIRTUAL TABLE courses_fts USING fts5(
            name, duration, fee, deadline, eligibility, highlights, why_join, content='courses', content_rowid='id'
        )
    ''')
    
    with open("data/raw_courses.json", "r", encoding="utf-8") as f:
        courses = json.load(f)
        
    for course in courses:
        cursor.execute('''
            INSERT INTO courses (name, duration, fee, deadline, eligibility, highlights, why_join)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        ''', (course['name'], course['duration'], course['fee'], course['deadline'], course['eligibility'], course['highlights'], course['why_join']))
        
        cursor.execute('''
            INSERT INTO courses_fts (rowid, name, duration, fee, deadline, eligibility, highlights, why_join)
            VALUES (last_insert_rowid(), ?, ?, ?, ?, ?, ?, ?)
        ''', (course['name'], course['duration'], course['fee'], course['deadline'], course['eligibility'], course['highlights'], course['why_join']))
        
    conn.commit()
    conn.close()
    
    print(f"Successfully generated enriched SQLite Database at {db_path} with FTS5 search enabled.")

if __name__ == "__main__":
    build_db()
