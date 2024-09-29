/**
 * @path TP02Q02 - Classe em c/Registro.java 
 * @description Java class of all Pokemon
 * @author Davi Cândido de Almeida - https://github.com/DaviKandido
 * @version 2.0
 * @update 2024-09-24
 */
// ---------------------------------------------------------------------------------------------------- //

//----------------------------- Start Structs --------------------------------//
// Includes
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <errno.h>

//----------------------------- Constantes --------------------------------//
#define MAX_POKEMONS 801
#define FILE_PATH "pokemon.csv"

#define MAX_GENERATION_DIGITOS 3
#define MAX_NAME 100
#define MAX_DESCRIPTION 350
#define MAX_TYPES 10
#define MAX_ABILITIES 10
#define MAX_WEIGHT_DIGITOS 20
#define MAX_HEIGHT_DIGITOS 20
#define MAX_CAPTURE_RATE_DIGITOS 3
#define MAX_DATE 11

#define MAX_LINE_SIZE 1000

//----------------------------- END Constantes --------------------------------//

//----------------------------- Start Structs --------------------------------//
typedef struct Date{

    int day;
    int month;
    int year;

} Date;

typedef struct Pokemon{

    int id;
    int generation;
    char name[MAX_NAME];
    char description[MAX_DESCRIPTION];
    char types[MAX_TYPES][MAX_NAME];
    char abilities[MAX_ABILITIES][MAX_NAME];
    double weight;
    double height;
    int captureRate;
    bool isLegendary;
    Date captureDate;
} Pokemon;

//----------------------------- END Structs --------------------------------//

//-------------------------- Global variables -----------------------------//

Pokemon pokemons[MAX_POKEMONS];
int PokemonsLength = 0;

//----------------------------- is Fim --------------------------------//
bool isFim(char* line) { return line[0] == 'F' && line[1] == 'I' && line[2] == 'M'; }

//----------------------------- substring--------------------------------//
void substring(char *string, char *stringStart, int length) {

    strncpy(string, stringStart, length);
    string[length] = '\0';
}

//--------------------------- proccess_attribute----------------------------//
void proccess_attribute (char *attribute, char **substringStart, char **substringEnd, bool isFirstAttribute, bool isStringArray){

    if(!isFirstAttribute) {
        
        if(*substringEnd != NULL) *substringStart = *substringEnd + 1;
        else *substringStart = *substringEnd;
    }

    if(!isStringArray) {

        if((*substringStart)[0] == '"') {
            
            *substringStart = *substringStart + 1;
            *substringEnd = strchr(*substringStart, '"');
        }
        else *substringEnd = strchr(*substringStart, ',');
        
        if(*substringEnd) {
            
            substring(attribute, *substringStart, *substringEnd - *substringStart);

            if(*substringEnd[0] == '"') *substringEnd = *substringEnd + 1;
        }
        else strcpy(attribute, *substringStart);

        if(strcmp(attribute, "") == 0 || attribute[0] == '\n' || attribute[0] == '\r' || attribute[0] == '\0') strcpy(attribute, "N/A");

        if(attribute[strlen(attribute) - 1] == '\n' || attribute[strlen(attribute) - 1] == '\r') attribute[strlen(attribute) - 1] = '\0';
    } 
    else {
        
        if((*substringStart)[0] == '[') {

            *substringStart = *substringStart + 1;

            if((*substringStart)[0] == ']') strcpy(attribute, ""); 
            else {

                char *tempConcat = (char *) calloc(MAX_LINE_SIZE, sizeof(char));

                *substringStart = *substringStart - 1;
         
                while(1) {

                    *substringStart = *substringStart + 1;

                    if((*substringStart)[0] == ',') break;
                    else if((*substringStart)[0] == '\'') { 
                           
                        *substringStart = *substringStart + 1;
                        *substringEnd = strchr(*substringStart, '\'');

                        if(*substringEnd) {

                            char tmp[MAX_LINE_SIZE];
                            substring(tmp, *substringStart, *substringEnd - *substringStart);

                            strcat(tempConcat, tmp);
                            strcat(tempConcat, ", ");

                            *substringStart = *substringEnd + 1;
                        }
                    }
                }

                strcpy(attribute, tempConcat);

                attribute[strlen(attribute) - 2] = '\0';
            }
        } 

        *substringEnd = strchr(*substringStart, ',');
    }
}

//------------------------- END proccess_attribute--------------------------//



//--------------------------- GETS----------------------------//

int pokemon_getId(Pokemon *pokemon) { return pokemon -> id;}
int pokemon_getGeneration(Pokemon *pokemon) { return pokemon -> generation;}
char *pokemon_getName(Pokemon *pokemon) { return pokemon -> name;}
char *pokemon_getDescription(Pokemon *pokemon) { return pokemon -> description;}

void pokemon_getTypes (Pokemon *pokemon, char *types) { 
    
    strcpy(types, "[");

    for(int i = 0; i < MAX_TYPES; i++) {

        if(strcmp(pokemon -> types[i], "") != 0) {

            strcat(types, pokemon -> types[i]);
            
            if(strcmp(pokemon -> types[i + 1], "") != 0) strcat(types, ", ");
        }
    }

    strcat(types, "]");
}

void pokemon_getAbilities (Pokemon *pokemon, char *abilities) { 
    
    strcpy(abilities, "[");

    for(int i = 0; i < MAX_ABILITIES; i++) {

        if(strcmp(pokemon -> abilities[i], "") != 0) {

            strcat(abilities, pokemon -> abilities[i]);
            
            if(strcmp(pokemon -> abilities[i + 1], "") != 0) strcat(abilities, ", ");
        }
    }

    strcat(abilities, "]");
}

double pokemon_getWeight (Pokemon *pokemon) {return pokemon -> weight; }
double pokemon_getHeight(Pokemon *pokemon) {return pokemon -> height; }
int pokemon_getCaptureRate(Pokemon *pokemon) {return pokemon -> captureRate; }
bool pokemon_getIsLegendary(Pokemon *pokemon) {return pokemon -> isLegendary; }

void pokemon_getCaptureDate(Pokemon *pokemon, char *captureDate) {
    
    if(pokemon -> captureDate.day != -1 && pokemon -> captureDate.month != -1 && pokemon -> captureDate.year != -1) {

        sprintf(captureDate, "%02d/%02d/%04d", pokemon -> captureDate.day, pokemon -> captureDate.month, pokemon -> captureDate.year);
    } else strcpy(captureDate, "");
}
//------------------------- END GETS --------------------------//


//--------------------------- SETS ----------------------------//
void pokemon_setId(Pokemon *pokemon, char *idStr) {
    char *endPtr;
    errno = 0;
    
    long id = strtol(idStr, &endPtr, 10);

    if (endPtr == idStr || errno == ERANGE || id > 850 || id < -1) {
        printf("Erro: Atributo '%s' não pôde ser convertido para int\n", idStr);
        pokemon->id = -1; 
    } else {
        pokemon->id = (int)id; 
    }
}
void pokemon_setGeneration(Pokemon *pokemon, char *generationStr) {
    char *endPtr;
    errno = 0;
    
    long generation = strtol(generationStr, &endPtr, 10);

    if (endPtr == generationStr || errno == ERANGE || generation > 850 || generation < -1) {
        printf("Erro: Atributo '%s' não pôde ser convertido para int\n", generationStr);
        pokemon->generation = -1; 
    } else {
        pokemon->generation = (int)generation;
    }
}
void pokemon_setName(Pokemon *pokemon, char *name) { strcpy(pokemon -> name, name); }
void pokemon_setDescription(Pokemon *pokemon, char *description) { strcpy(pokemon -> description, description); }

void pokemon_setTypes(Pokemon *pokemon, char *types) {

    char tempTypes[MAX_LINE_SIZE];
    strcpy(tempTypes, types);

    char *token = strtok(tempTypes, ",");
    int i = 0;

    while (token != NULL && i < MAX_TYPES) {
        
        while (*token == ' ') token++;

        int len = strlen(token);

        while (len > 0 && token[len - 1] == ' ') {

            token[len - 1] = '\0';
            len--;
        }

        strcpy(pokemon -> types[i++], token);
        token = strtok(NULL, ",");
    }
}

void pokemon_setAbilities(Pokemon *pokemon, char *abilities) {

    char tempAbilities[MAX_LINE_SIZE];
    strcpy(tempAbilities, abilities);

    char *token = strtok(tempAbilities, ",");
    int i = 0;

    while (token != NULL && i < MAX_ABILITIES) {
        
        while (*token == ' ') token++;

        int len = strlen(token);

        while (len > 0 && token[len - 1] == ' ') {

            token[len - 1] = '\0';
            len--;
        }

        strcpy(pokemon -> abilities[i++], token);
        token = strtok(NULL, ",");
    }
}

void pokemon_setWeight(Pokemon *pokemon, char *weightStr) {
    char *endPtr;  
    errno = 0;     
    
    double weight = strtod(weightStr, &endPtr);

    if (endPtr == weightStr || errno == ERANGE) {
        printf("Erro: Atributo '%s' não pôde ser convertido para double\n", weightStr);
        pokemon->weight = 0.0; 
    } else {
        pokemon->weight = weight;
    }
}


void pokemon_setHeight(Pokemon *pokemon, char *heightStr) {
    char *endPtr;  
    errno = 0;     
    
    double height = strtod(heightStr, &endPtr);

    if (endPtr == heightStr || errno == ERANGE) {
        printf("Erro: Atributo '%s' não pôde ser convertido para double\n", heightStr);
        pokemon->height = 0.0; 
    } else {
        pokemon->height = height; 
    }
}

void pokemon_setCaptureRate(Pokemon *pokemon, char *captureRateStr) {
    char *endPtr; 
    errno = 0;      
    
    long captureRate = strtol(captureRateStr, &endPtr, 10);

    if (endPtr == captureRateStr || errno == ERANGE || captureRate > 850 || captureRate < -1) {
        printf("Erro: Atributo '%s' não pôde ser convertido para int\n", captureRateStr);
        pokemon->captureRate = -1; 
    } else {
        pokemon->captureRate = (int)captureRate;
    }
}

void pokemon_setIsLegendary(Pokemon *pokemon, bool isLegendary) { pokemon -> isLegendary = isLegendary; }

void pokemon_setCaptureDate(Pokemon *pokemon, char *captureDate) { 

    if(strlen(captureDate) >= 8 && strlen(captureDate) <= 10) {

        char *token = strtok(captureDate, "/");

        pokemon -> captureDate.day = atoi(token);
        token = strtok(NULL, "/");
        pokemon -> captureDate.month = atoi(token);
        token = strtok(NULL, "/");
        pokemon -> captureDate.year = atoi(token);
    }
}
//------------------------- END SETS --------------------------//


//--------------------------- Pokemon_newBlanck ----------------------------//
Pokemon Pokemon_newBlack() {
    Pokemon pokemon;

    pokemon.id = -1;
    pokemon.generation = -1;
    strcpy(pokemon.name, "");
    strcpy(pokemon.description, "");

    for(int i = 0; i < MAX_TYPES; i++) {
        strcpy(pokemon.types[i], "");
    }

   for(int i = 0; i < MAX_ABILITIES; i++) {
        strcpy(pokemon.abilities[i], "");
    }

    pokemon.weight = -1;
    pokemon.height = -1;
    pokemon.captureRate = -1;
    pokemon.isLegendary = false;
    pokemon.captureDate.day = -1;
    pokemon.captureDate.month = -1;
    pokemon.captureDate.year = -1;

    return pokemon;
}

//------------------------- END Pokemon_newBlanck --------------------------//

//--------------------------- Pokemon_print ----------------------------//
void pokemon_print(Pokemon *pokemon) {
    char formattedDate[MAX_DATE]; 
    pokemon_getCaptureDate(pokemon, formattedDate);

    char types[MAX_LINE_SIZE];
    pokemon_getTypes(pokemon, types);

    char abilities[MAX_LINE_SIZE];
    pokemon_getAbilities(pokemon, abilities);

    printf("[#%d -> %s: %s - %s - %s - %.2fkg - %.2fm - %d%% - %s - %d gen] - %s\n", 
           pokemon->id, 
           pokemon->name, 
           pokemon->description,
           types,
           abilities,
           pokemon->weight, 
           pokemon->height, 
           pokemon->captureRate, 
           pokemon->isLegendary ? "true" : "false", 
           pokemon->generation, 
           formattedDate);
}

//------------------------- END Pokemon_print --------------------------//

//----------------------------- Pokemon_read --------------------------------//
Pokemon Pokemon_read(char *line){

    Pokemon pokemon = Pokemon_newBlack();

    char *substringStart = line;
    char *substringEnd = NULL;
    char attribute[MAX_LINE_SIZE];


    proccess_attribute(attribute, &substringStart, &substringEnd, true, false);
    pokemon_setId(&pokemon,attribute); 

    proccess_attribute(attribute, &substringStart, &substringEnd, false, false);
    pokemon_setGeneration(&pokemon, attribute); 

    proccess_attribute(attribute, &substringStart, &substringEnd, false, false);
    pokemon_setName(&pokemon, attribute);

    proccess_attribute(attribute, &substringStart, &substringEnd, false, false);
    pokemon_setDescription(&pokemon, attribute);

    proccess_attribute(attribute, &substringStart, &substringEnd, false, true);
    pokemon_setTypes(&pokemon, attribute);

    proccess_attribute(attribute, &substringStart, &substringEnd, false, true);
    pokemon_setAbilities(&pokemon, attribute);

    proccess_attribute(attribute, &substringStart, &substringEnd, false, false);
    pokemon_setWeight(&pokemon, attribute); 

    proccess_attribute(attribute, &substringStart, &substringEnd, false, false);
    pokemon_setHeight(&pokemon, attribute); 

    proccess_attribute(attribute, &substringStart, &substringEnd, false, false);
    pokemon_setCaptureRate(&pokemon, attribute); 

    proccess_attribute(attribute, &substringStart, &substringEnd, false, false);
    pokemon_setIsLegendary(&pokemon, strcmp(attribute, "0") == 0);

    if(attribute[strlen(attribute) - 1] == '\n' || attribute[strlen(attribute) - 1] == '\r') attribute[strlen(attribute) - 1] = '\0';

    proccess_attribute(attribute, &substringStart, &substringEnd, false, false);
    pokemon_setCaptureDate(&pokemon, attribute);

    return pokemon;
}
//--------------------------- END Pokemon_read ------------------------------//

//---------------------------  Pokemon_searchById ------------------------------//
Pokemon *pokemon_searchById(int id) {

    for(int i = 0; i < PokemonsLength; i++) {
        if(pokemons[i].id == id) return &pokemons[i];
    }
    return NULL;
}

//--------------------------- END Pokemon_searchById ----------------------------//

//----------------------------- startPokemons --------------------------------//

void startPokemons(){ 

   FILE *fp;
    char *line = NULL;
    size_t len = 0;
    ssize_t read;

    fp = fopen(FILE_PATH, "r");

    if(fp == NULL) {

        perror("x Error ao abrir arquivo");
        exit(EXIT_FAILURE);
    }

    getline(&line, &len, fp);

    while((read = getline(&line, &len, fp)) != -1) {

        Pokemon pokemon = Pokemon_read(line);

        pokemons[PokemonsLength++] = pokemon;

        if(PokemonsLength >= MAX_POKEMONS) {

            perror("x Max characters reached");
            exit(EXIT_FAILURE);
        }
    }

    fclose(fp);

    if(line) free(line);
}
//--------------------------- END startPokemons ------------------------------//


int main() {

    startPokemons();

    char id[10];
    scanf(" %[^\n]s", id);
    bool parar = true;

    while(parar) {

        if(id[strlen(id) - 1] == '\n' || id[strlen(id) - 1] == '\r') id[strlen(id) - 1] = '\0';

        if(isFim(id)) {
            parar = false;
        }else {

            int id_int = atoi(id);

            Pokemon *pokemon = pokemon_searchById(id_int);

            if(pokemon) pokemon_print(pokemon);
            else printf("x Pokemon not found!\n");
    
            scanf(" %[^\n]s", id);
        }
    }
    return 0;
}