#include<stdio.h>
#include<stdlib.h>

typedef struct{
    int *elem;/*˳���Ĵ洢�ռ��ַ*/
    int length;/*����*/
    int listsize;/*�洢����*/
}SQList;

int ListSize;
void Delete_Repeat(SQList *L);/*ɾ���ظ���*/
void Inverse(SQList *L);/*����*/
void Show(SQList *L);/*�����ʾ*/
void Init(SQList *L);/*����˳���*/
int main(){
    SQList l;
    Init(&l);
    Delete_Repeat(&l);
    Inverse(&l);
    getch();
    return 0;

}/*������*/

void Inverse(SQList *L){/*���ú����*/
    int i = 0;
    int j = L->length-1;
    int *p = L->elem;
    int temp;
    for(i = 0,j = L->length-1;i<j;i++,j--){
       temp = p[i];
       p[i] = p[j];
       p[j] = temp;
   }
   printf("after INVERSE  ");
   Show(L);
}
void Delete_Repeat(SQList *L){/*ɾ���ظ���*/
    int i = 1;
    int *p = L->elem;
    int *q;
    int *t ,*r;
    int k = 0;
    int j;
    int g = 0;
    int h;
    while(i<=L->length){
            t = p+1;
        if(*p == *(t)&&*t != NULL){
            q = p;
            k = 0;
            r = q+1;
            while(*q==*r)
            {
                k++;
                r++;
            }
            for(j = k+i-1;j<=L->length-1;j++)
            {
                L->elem[j-k] = L->elem[j];

            }
            L->length = L->length - k;
        g = L->length;
        for(h = 0 ;h<k;h++)
            {L->elem[g] = NULL;
            g += 1;}
        }

        p++;
        i++;
    }
    printf("after DELETE  ");
    Show(L);
}
void Init(SQList *L){/*����˳���*/
    int i = 0;

    printf("the number you want to input:\n");
    scanf("%d",&ListSize);
    *L->elem =(SQList*)malloc(ListSize*sizeof(SQList));

    printf("please input %d number(s)\n",ListSize);
    for(i=0;i<ListSize;i++){
        scanf("%d",&L->elem[i]);
    }

    L->length = ListSize;
    Show(L);
}
void Show(SQList *L){ /*���*/
    int i = 0;
    printf("the list :\n");
    for(i=0;i<L->length;i++)
        printf("%d  ",L->elem[i]);
    printf("\n");
}
