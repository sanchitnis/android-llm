import os
import json

def expand_dataset():
    programs = [
        {
            "name": "B.Tech Computer Science and Engineering", "duration": "4 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "Pass in 10+2 with Physics and Mathematics as compulsory subjects along with Chemistry/Biotechnology/Biology/Technical Vocational subject. Minimum 45% marks (40% for SC/ST).",
            "highlights": "Focus on AI, Cloud Computing, Cybersecurity, and Software Engineering. High placement record with top tech firms.",
            "why_join": "Industry-aligned curriculum, state-of-the-art labs, and strong placement assistance in global IT companies."
        },
        {
            "name": "B.Tech Artificial Intelligence and Data Science", "duration": "4 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "Pass in 10+2 with Physics and Mathematics as compulsory subjects. Minimum 45% marks.",
            "highlights": "Specialized labs for Machine Learning, Deep Learning, and Big Data Analytics.",
            "why_join": "Prepares students for the highest-growing sector in tech with hands-on projects and internships."
        },
        {
            "name": "B.Tech Electronics and Communication Engineering", "duration": "4 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "Pass in 10+2 with Physics and Mathematics as compulsory subjects. Minimum 45% marks.",
            "highlights": "VLSI Design, Embedded Systems, IoT, and 5G Communications.",
            "why_join": "Blend of hardware and software skills, opening opportunities in core electronics and IT sectors."
        },
        {
            "name": "B.Tech Mechanical Engineering", "duration": "4 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "Pass in 10+2 with Physics, Mathematics, and Chemistry. Minimum 45% marks.",
            "highlights": "Robotics, Automation, CAD/CAM, and 3D Printing facilities.",
            "why_join": "Core engineering foundation with modern technological integration for robust career paths."
        },
        {
            "name": "B.Tech Civil Engineering", "duration": "4 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "Pass in 10+2 with Physics, Mathematics, and Chemistry. Minimum 45% marks.",
            "highlights": "Smart City Planning, Structural Engineering, and Environmental Science.",
            "why_join": "Contribute to infrastructure development with practical surveying and material testing experience."
        },
        {
            "name": "B.Arch (Bachelor of Architecture)", "duration": "5 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "Pass in 10+2 with PCM or 10+3 Diploma with Mathematics. Minimum 50% marks. Valid NATA score required.",
            "highlights": "Design studios, model-making workshops, and sustainable architecture focus.",
            "why_join": "Develop creative and technical skills to design the built environment of the future."
        },
        {
            "name": "BBA (Bachelor of Business Administration)", "duration": "3 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "Pass in 10+2 or equivalent from a recognized board.",
            "highlights": "Specializations in Marketing, Finance, HR, and Entrepreneurship.",
            "why_join": "Develop leadership and management skills for corporate and entrepreneurial success."
        },
        {
            "name": "B.Com (Professional)", "duration": "3 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "Pass in 10+2 or equivalent from a recognized board.",
            "highlights": "Integrated with professional certifications like CA/CS/CMA foundation.",
            "why_join": "Strong foundation in accounting, taxation, and financial management."
        },
        {
            "name": "BCA (Bachelor of Computer Applications)", "duration": "3 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "Pass in 10+2 or equivalent.",
            "highlights": "Programming, Web Development, Database Management, and Networking.",
            "why_join": "Fast-track entry into the IT industry with practical coding skills."
        },
        {
            "name": "BA LL.B (Hons.)", "duration": "5 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "Pass in 10+2 or equivalent with minimum 45% marks. Valid CLAT/LSAT score preferred.",
            "highlights": "Moot court competitions, legal aid clinics, and internships with top law firms.",
            "why_join": "Comprehensive legal education combining arts foundation with rigorous legal training."
        },
        {
            "name": "BBA LL.B (Hons.)", "duration": "5 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "Pass in 10+2 or equivalent with minimum 45% marks.",
            "highlights": "Corporate law focus, moot courts, and management integration.",
            "why_join": "Ideal for students aiming for careers in corporate law, management, and legal consultancy."
        },
        {
            "name": "B.Sc. Biotechnology, Biochemistry, Genetics", "duration": "3 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "Pass in 10+2 with Physics, Chemistry, and Biology/Mathematics.",
            "highlights": "Advanced life science labs, research projects, and industry tie-ups.",
            "why_join": "Prepares for careers in research, pharmaceuticals, and healthcare sectors."
        },
        {
            "name": "B.Sc. Bioinformatics", "duration": "3 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "Pass in 10+2 with Physics, Chemistry, Mathematics/Biology.",
            "highlights": "Computational biology, genomics, and programming for biological data.",
            "why_join": "Join the cutting-edge field combining biology and computer science."
        },
        {
            "name": "BA Journalism and Mass Communication", "duration": "3 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "Pass in 10+2 or equivalent.",
            "highlights": "Media studios, digital journalism, public relations, and film production.",
            "why_join": "Develop excellent communication skills for media, broadcasting, and corporate communications."
        },
        {
            "name": "M.Tech Computer Science and Engineering", "duration": "2 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "B.E/B.Tech in relevant branch with minimum 50% marks. PGCET/GATE score required.",
            "highlights": "Advanced research in AI, Machine Learning, and Network Security.",
            "why_join": "Specialized knowledge for higher-level R&D and academic roles."
        },
        {
            "name": "MBA (Master of Business Administration)", "duration": "2 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "Bachelor's degree with minimum 50% marks. Valid MAT/CAT/PGCET score required.",
            "highlights": "Case-study methodology, industry internships, and global exposure programs.",
            "why_join": "Accelerate career growth into executive and leadership positions."
        },
        {
            "name": "MCA (Master of Computer Applications)", "duration": "2 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "BCA/B.Sc/B.Com/BA with Mathematics at 10+2 or degree level. Minimum 50% marks.",
            "highlights": "Advanced software development, AI integration, and cloud architecture.",
            "why_join": "Master advanced computing concepts for high-paying IT roles."
        },
        {
            "name": "LL.M (Master of Laws)", "duration": "1 Year", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "LL.B degree with minimum 50% marks.",
            "highlights": "Specializations in Corporate Law and Criminal Law. Research-focused.",
            "why_join": "Advanced legal specialization for academia, judiciary, or specialized practice."
        },
        {
            "name": "M.Sc. Biotechnology", "duration": "2 Years", "fee": "Contact Admissions", "deadline": "August 2026",
            "eligibility": "B.Sc with relevant life science subjects. Minimum 50% marks.",
            "highlights": "Molecular biology research, industrial biotechnology, and bioethics.",
            "why_join": "Deepen scientific expertise for advanced research and biotech industry roles."
        }
    ]
    
    os.makedirs("data", exist_ok=True)
    
    with open("data/raw_courses.json", "w", encoding="utf-8") as f:
        json.dump(programs, f, indent=4)
        
    html_content = "<html><body>\n"
    for p in programs:
        html_content += f"<div>{p['name']} | {p['duration']} | {p['fee']} | {p['deadline']} | {p['eligibility']} | {p['highlights']} | {p['why_join']}</div>\n"
    html_content += "</body></html>"
    
    with open("data/source.html", "w", encoding="utf-8") as f:
        f.write(html_content)
        
    print(f"Successfully generated {len(programs)} programs with enriched data.")

if __name__ == "__main__":
    expand_dataset()
