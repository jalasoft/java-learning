#!/bin/bash

java -javaagent:/home/FIO_DOM/lastovicka/Workspaces/agent-prototyping/my-agent/target/my-agent.jar=package:cz.jalasoft.* -jar /home/FIO_DOM/lastovicka/Workspaces/agent-prototyping/my-app/target/my-app-1.0-SNAPSHOT.jar &>out.txt &

