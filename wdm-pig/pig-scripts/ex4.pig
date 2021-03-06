A  = LOAD 'wdm-pig/content/TitleAndActor.txt' USING PigStorage('\t') AS ( 
    TITLE:chararray,
    ACTOR_NAME:chararray,
    BIRTH_DATE:int,
    ROLE:chararray);
D  = LOAD 'wdm-pig/content/DirAndTitle.txt' USING PigStorage('\t') AS ( 
    DIR_NAME:chararray,
    TITLE:chararray,
    REL_YEAR:int);
X = COGROUP A by ACTOR_NAME INNER, D BY DIR_NAME INNER;
result = FOREACH X GENERATE $0, $1.TITLE, $2.TITLE;
STORE result INTO 'wdm-pig/pig-results/ex4';
