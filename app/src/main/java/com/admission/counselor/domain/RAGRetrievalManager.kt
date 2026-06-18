package com.admission.counselor.domain

import com.admission.counselor.data.db.CourseDao
import com.admission.counselor.data.db.CourseEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RAGRetrievalManager @Inject constructor(
    private val courseDao: CourseDao
) {
    suspend fun retrieveContext(query: String): String {
        // Extract basic keywords for FTS5. In a real app, you might use an NLP token extractor or LLM pre-pass.
        val keywords = extractKeywords(query)
        
        val results = try {
            courseDao.searchCourses(keywords)
        } catch (e: Exception) {
            // Fallback if FTS5 syntax fails
            courseDao.getFallbackCourses()
        }
        
        if (results.isEmpty()) return "No specific context found."
        
        return buildContextString(results)
    }
    
    private fun extractKeywords(query: String): String {
        // Simple keyword extraction: remove common stop words or just pass query
        // FTS5 MATCH requires specific syntax, so we replace spaces with OR
        return query.split(Regex("\\s+"))
            .filter { it.length > 2 }
            .joinToString(" OR ") { "\"$it\"*" }
    }
    
    private fun buildContextString(courses: List<CourseEntity>): String {
        return courses.joinToString("\n---\n") { course ->
            """
            Course: ${course.name}
            Duration: ${course.duration}
            Fee: ${course.fee}
            Deadline: ${course.deadline}
            Eligibility: ${course.eligibility}
            Highlights: ${course.highlights}
            Why Join: ${course.why_join}
            """.trimIndent()
        }
    }
}
