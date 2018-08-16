alias ls=/bin/ls
unset PROMPT_COMMAND
PS1='$ '

compileJ=$(find . -name '*.java')
javac -d ./compiled $compileJ #-Xlint
java -cp compiled $1 -classpath