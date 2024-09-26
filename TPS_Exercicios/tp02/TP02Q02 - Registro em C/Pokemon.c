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


//----------------------------- Constantes --------------------------------//

#define MAX_Pokemons 801

//#define FILE_PATH "/tmp/pokemon.csv"
#define FILE_PATH "pokemon.csv"


#define MAX_generation 300
#define MAX_name 100
#define MAX_description 350
#define MAX_types 10
#define MAX_abilities 10
#define MAX_weight 200
#define MAX_height 250

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
    char *name;
    char *description;
    char *types[10];
    char *abilities[10];
    double weight;
    double height;
    int captureRate;
    bool isLegendary;
    Date captureDate;
} Pokemon;

//----------------------------- END Structs --------------------------------//

//-------------------------- Global variables -----------------------------//

Pokemon pokemons[1000];
int PokemonsLength = 0;

//----------------------------- is Fim --------------------------------//
bool isFim(char* line) { return line[0] == 'F' && line[1] == 'I' && line[2] == 'M'; }

//----------------------------- substring--------------------------------//
void substring(char *string, char *stringStart, int length) {

    strncpy(string, stringStart, length);
    string[length] = '\0';
}

//--------------------------- proccess_attribute----------------------------//

//Verificar se esta correto
void proccess_attribute (char *attribute, char **substringStart, char **substringEnd, bool isFirstAttribute, bool isStringArray){

    // Skip first comma
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
        
        // Get substring
        if(*substringEnd) {
            
            substring(attribute, *substringStart, *substringEnd - *substringStart);

            if(*substringEnd[0] == '"') *substringEnd = *substringEnd + 1;
        }
        else strcpy(attribute, *substringStart);

        // Set default value if attribute is empty
        if(strcmp(attribute, "") == 0 || attribute[0] == '\n' || attribute[0] == '\r' || attribute[0] == '\0') strcpy(attribute, "N/A");

        // Clean \n from the end of the string
        if(attribute[strlen(attribute) - 1] == '\n' || attribute[strlen(attribute) - 1] == '\r') attribute[strlen(attribute) - 1] = '\0';
    } 
    else {
        
        // Check if the first character is a [
        if((*substringStart)[0] == '[') {

            *substringStart = *substringStart + 1;

            if((*substringStart)[0] == ']') strcpy(attribute, ""); // Case: []
            else {

                char *tempConcat = (char *) calloc(MAX_LINE_SIZE, sizeof(char));

                *substringStart = *substringStart - 1;
         
                while(1) {

                    *substringStart = *substringStart + 1;

                    if((*substringStart)[0] == ',') break;
                    else if((*substringStart)[0] == '\'') { // Case: "['example', 'example']"
                           
                        *substringStart = *substringStart + 1;
                        *substringEnd = strchr(*substringStart, '\'');

                        // Get substring
                        if(*substringEnd) {

                            // Create tmp name
                            char tmp[MAX_LINE_SIZE];
                            substring(tmp, *substringStart, *substringEnd - *substringStart);

                            // Concat tempConcat with tmp
                            strcat(tempConcat, tmp);
                            strcat(tempConcat, ", ");

                            *substringStart = *substringEnd + 1;
                        }
                    }
                }

                // Get substring
                strcpy(attribute, tempConcat);

                // Clean "attribute" removing last 2 characters
                attribute[strlen(attribute) - 2] = '\0';
            }
        } 

        *substringEnd = strchr(*substringStart, ',');
    }
}

//------------------------- END proccess_attribute--------------------------//

//--------------------------- GETS----------------------------//

//------------------------- END GETS --------------------------//

//--------------------------- SETS ----------------------------//

//------------------------- END SETS --------------------------//


//--------------------------- Pokemon_newBlanck ----------------------------//

Pokemon Pokemon_newBlack() {
    Pokemon pokemon;

    pokemon.id = -1;

    pokemon.generation = -1;

    pokemon.name = (char *) calloc(MAX_name, sizeof(char));
    strcpy(pokemon.name, "");

    pokemon.description= (char *) calloc(MAX_description, sizeof(char));
    strcpy(pokemon.description, "");

    // inicializa types
    for(int i = 0; i < MAX_types; i++) {
        pokemon.types[i] = (char *) calloc(MAX_types, sizeof(char));
        strcpy(pokemon.types[i], "");
    }

   // inicializa abilities
    for(int i = 0; i < MAX_abilities; i++) {
        pokemon.abilities[i] = (char *) calloc(MAX_abilities, sizeof(char));
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

//--------------------------- Pokemon_new ----------------------------//

//------------------------- END Pokemon_new --------------------------//

//--------------------------- Pokemon_Clone ----------------------------//

//------------------------- END Pokemon_Clone --------------------------//

//--------------------------- Pokemon_print ----------------------------//

//------------------------- END Pokemon_print --------------------------//

//----------------------------- Pokemon_read --------------------------------//
Pokemon Pokemon_read(char *line){

    Pokemon pokemon = Pokemon_newBlack();
zz    char *substringStart = line;
    char *substringEnd = NULL;
    char attribute[MAX_LINE_SIZE];

    proccess_attribute(attribute, &substringStart, &substringEnd, true, false);
}
//--------------------------- END Pokemon_read ------------------------------//


//----------------------------- startPokemons --------------------------------//

void startPokemons(){ // Função inicia leituras de pokemons

   // Open file
    FILE *fp;
    char *line = NULL;
    size_t len = 0;
    ssize_t read;

    fp = fopen(FILE_PATH, "r");

    if(fp == NULL) {

        perror("x Error opening file");
        exit(EXIT_FAILURE);
    }

    // Skip first line
    getline(&line, &len, fp);

        // Read all lines
    while((read = getline(&line, &len, fp)) != -1) {

        // Read character from line
        Pokemon pokemon = Pokemon_read(line);

        pokemons[PokemonsLength++] = pokemon;

        if(PokemonsLength >= 801) {

            perror("x Max characters reached");
            exit(EXIT_FAILURE);
        }
    }

    // Close file and free memory
    fclose(fp);

    if(line) free(line);
}
//--------------------------- END startPokemons ------------------------------//


int main() {

    // ----------------------------------------------------------------- //

    // #1 - Start - Read all characters from file
    startPokemons();

    // ----------------------------------------------------------------- //

    //#2 - Read input and print characters from pub.in id entries
    char id[10];
    scanf(" %[^\n]s", id);
    bool parar = true;

    while(parar) {

        // Clean \n from the end of the string
        if(id[strlen(id) - 1] == '\n' || id[strlen(id) - 1] == '\r') id[strlen(id) - 1] = '\0';

        if(isFim(id)) {
            parar = false;
        }else {
            
            int id_int = (int) id;

            Pokemon *pokemon = pokemon_searchById(id_int);

            if(pokemon) pokemon_print(pokemon);
            else printf("x Pokemon not found!\n");

            // ------------------------- //
    
            scanf(" %[^\n]s", id);
        }
    }
    return 0;
}