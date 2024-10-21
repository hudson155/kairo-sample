select *
from <tableName>
where id = :id
  and deleted_at is null
<lockingClause>
