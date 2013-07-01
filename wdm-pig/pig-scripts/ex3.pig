A  = LOAD 'wdm-pig/content/TitleAndActor.txt' USING PigStorage('\t') AS ( 
    TITLE:chararray,
    ACTOR_NAME:chararray,
    BIRTH_DATE:int,
    ROLE:chararray);
D  = LOAD 'wdm-pig/content/DirAndTitle.txt' USING PigStorage('\t') AS ( 
    DIR_NAME:chararray,
    TITLE:chararray,
    REL_YEAR:int);
X = COGROUP A by TITLE, D BY TITLE;
result = FOREACH X GENERATE $0, $2.DIR_NAME, $1.ACTOR_NAME;
STORE result INTO 'wdm-pig/pig-results/ex3';
