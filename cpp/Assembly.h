#pragma once

#include "Delimiter.h"

enum UserNameType
{
   NT_Label,
   NT_Var,
   NT_Segment
};

struct UserName
{
   UserNameType type;

   end_token token;

   int offset {0};
   int begin {0};
   int end {0};
};

extern vector<UserName> userNames;

UserName* getUserName(UserNameType type, const string& name);
UserName* getUserName(UserNameType type, int line);

string padTo(int input, const size_t num, const char paddingChar = ' ', bool hex = false);

int64_t tokenToNumber(end_token* token);

void printTables();
void printLexemeList();
void outputErrors();

void analyzeSegments();
void analyzeVariablesAndLabels();
void analyzeOperandTypes(); 
void checkInstructionRequirements();
void calculateSize();