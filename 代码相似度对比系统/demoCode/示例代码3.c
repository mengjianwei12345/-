#include<stdio.h>                /*ͷ�ļ�*/
#define LIST_INIT_SIZE 100       /*˳���洢�ռ�ĳ�ʼ������*/
struct list{
int *data;                       /*˳���Ļ�ַ*/
int length;                      /*��ǰ����*/
int listsize;                    /*��ǰ����Ĵ洢����*/
};
typedef struct list sqlist;      /*���α��˳��洢�ṹ����*/
int InitList_sqlist(sqlist *);   /*����һ���µ�˳��� */
int ListSert(sqlist *,int,int);  /*�����µ�Ԫ��*/
int delete_data(sqlist *);       /*ɾ���ظ���Ԫ��*/
int nixu(sqlist *);              /*����*/
void printlist_sqlist(sqlist);   /*���*/
int main()                       /*����������*/
{
  sqlist la;                     /*˳���*/
  InitList_sqlist(&la);
   ListSert(&la,1,9);
    ListSert(&la,2,7);
     ListSert(&la,3,6);
      ListSert(&la,4,5);
       ListSert(&la,5,4);
        ListSert(&la,6,4);
         ListSert(&la,7,2);
          ListSert(&la,8,1);
           ListSert(&la,9,1);
            ListSert(&la,10,0);
    printf("display the list:\n");    /*�����ʾ*/
    printlist_sqlist(la);             /*���la��*/

   printf("delete the same data:\n"); /*�����ʾ*/
   delete_data(&la);                  /*ɾ������*/
   printlist_sqlist(la);              /*���la��*/
   printf("contrary the data:\n");    /*�����ʾ*/

    nixu(&la);
    printlist_sqlist(la);             /*��������*/
     getch();
     return 0;
}
int InitList_sqlist(sqlist *L)        /*����һ����˳���L*/
{
  L->data=(int *)malloc(LIST_INIT_SIZE*sizeof(int));   /*�����ڴ�*/
  if(!L->data) exit(0);                                /*�洢����ʧ�ܣ��˳�*/
  L->length=0;                                         /*�ձ���Ϊ0*/
  L->listsize=LIST_INIT_SIZE;                          /*��ʼ�洢����*/
  return 1;
}
int delete_data(sqlist *L)                             /*ɾ��˳���L�е���*/
{
 int i=1;
 int *p,*q,*l;
 p=&(L->data[i-1]);
 q=p+1;
 for(p;q<=&(L->data[L->length-1]);q++)
 {  if(*p==*q)  {                                      /*���ظ�*/

  for(p;q<=&(L->data[L->length-1]);q++)
   {

          *p=*q;
          p++;
   }
        p=&(L->data[i-1]); q=p;
   L->length--;
   }
   else {p++; }
   }

}

void printlist_sqlist(sqlist L)                        /*���˳���*/
{
  int i;
  for(i=0;i<L.length;i++)
  {
    printf("%d ",L.data[i]);
  }
  printf("\n");
}
int ListSert(sqlist *L,int i,int e)
{                                                      /*�����µ�Ԫ��*/
  int *p,*q;
  q=&(L->data[i-1]);
  for(p=&L->data[L->length-1];p>q;--p)
  *(p+1)=*p;
  *q=e;
  L->length++;
  return 1;
}
int nixu(sqlist *L)                                    /*����*/
{                                                      
   int i=1;
  int *p,*q,*l;
  p=&L->data[i-1];
  q=&L->data[L->length-1];
  if(L->length%2==0)
  for(i=1;i<=(L->length)/2;i++)
  {
    *l=*p;
    *p=*q;

    *q=*l;
    ++p;
    --q;
  }

  else
  for(i=1;i<=(L->length-1)/2;i++)
  {
    *l=*p;
    *p=*q;

    *q=*l;
  }
}
