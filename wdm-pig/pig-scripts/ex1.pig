title_and_actor  = LOAD 'wdm-pig/content/TitleAndActor.txt' USING PigStorage('\t') AS ( 
                    TITLE:chararray,
                    ACTOR_NAME:chararray,
                    BIRTH_DATE:int,
                    ROLE:chararray);
grouped_by_title = GROUP title_and_actor BY TITLE;
result = FOREACH grouped_by_title GENERATE $0, $1.(ACTOR_NAME, ROLE);
STORE result INTO 'wdm-pig/pig-results/ex1';

