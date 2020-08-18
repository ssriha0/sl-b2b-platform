#ifndef _GLOBALS_H 
#define _GLOBALS_H

//--------------------------------------------------------------------
// Include Files
#include "lrun.h"
#include "web_api.h"
#include "lrw_custom_body.h"

//--------------------------------------------------------------------
// Global Variables
// 
	int i;
//char line [] = "108, 'Network Attached Storage (NAS)', 100";
char delimiters[]=",";
char * parentCategoryIdPointer;
char * categoryNamePointer;
char * categoryIdPointer; // 108
char * providerIdPointer;
char * providerNamePointer;
int stringLength;
char categoryId [25];
char categoryName [250];
char parentCategoryId [50];
char providerId[50];
char providerId1[50];
char providerName[50];
int count;
int value;
char random_value[20];
char out_value[20];
char delimiter[]=",";
char projectTypeId[20];


#endif // _GLOBALS_H
