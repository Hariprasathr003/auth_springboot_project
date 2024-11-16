CREATE OR REPLACE VIEW topic_summary_view AS
SELECT 
    topics.id,
    topics.name AS topics_name,
    topics.created_at,
    category.name AS category_name
FROM 
    topics
INNER JOIN 
    category
ON 
    topics.category_uuid = category.uuid;
