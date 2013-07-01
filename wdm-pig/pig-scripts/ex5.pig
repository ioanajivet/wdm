A  = LOAD 'wdm-pig/content/TitleAndActor.txt' USING PigStorage('\t') AS ( 
    TITLE:chararray,
    ACTOR_NAME:chararray,
    BIRTH_DATE:int,
    ROLE:chararray);
D  = LOAD 'wdm-pig/content/DirAndTitle.txt' USING PigStorage('\t') AS ( 
    DIR_NAME:chararray,
    TITLE:chararray,
    REL_YEAR:int);
X = JOIN A by TITLE, D BY TITLE;
F = FILTER X BY $1==$4;
result = FOREACH F GENERATE $1;
STORE result INTO 'wdm-pig/pig-results/ex5';
