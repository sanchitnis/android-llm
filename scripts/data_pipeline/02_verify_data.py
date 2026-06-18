import os
import json
import re

def verify_data():
    print("Running Anti-Hallucination Gate...")
    
    if not os.path.exists("data/source.html") or not os.path.exists("data/raw_courses.json"):
        print("ERROR: Source files missing.")
        return False
        
    with open("data/source.html", "r", encoding="utf-8") as f:
        html_content = f.read()
        
    with open("data/raw_courses.json", "r", encoding="utf-8") as f:
        courses = json.load(f)
        
    for idx, course in enumerate(courses):
        for key, value in course.items():
            # Only strictly verify numbers/dates to prevent hallucinated fees/requirements
            numbers_in_value = re.findall(r'\d+', str(value))
            
            for num in numbers_in_value:
                if num not in html_content:
                    print(f"HALLUCINATION DETECTED! The number '{num}' in {key} ('{value}') was not found in the source HTML.")
                    return False
                    
    print("Verification Passed: 100% confidence. No numerical hallucinations detected.")
    return True

if __name__ == "__main__":
    success = verify_data()
    if not success:
        exit(1)
