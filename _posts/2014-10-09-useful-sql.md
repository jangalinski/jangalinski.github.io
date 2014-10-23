---
layout: post
published: false
---

![united-states-vs-the-rest-of-the-world.jpg](/media/united-states-vs-the-rest-of-the-world.jpg)

Counts all started processes after given date and groups by definition key.

     select 
       pd.KEY_, count(pd.KEY_) as instances
     from 
       act_hi_procinst pi
       inner join ACT_RE_PROCDEF pd on pd.id_ = pi.proc_def_id_
     where
       pi.START_TIME_ >  to_date('2014-09-30','YYYY-MM-DD')
       and pi.end_time_ is null
     group by
       pd.KEY_
     order by instances;

