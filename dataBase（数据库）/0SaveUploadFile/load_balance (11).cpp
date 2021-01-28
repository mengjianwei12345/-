#include <winsock2.h>
#include <iphlpapi.h>
#include <stdio.h>
#include <stdlib.h>
#include <conio.h>

#pragma comment(lib, "Ws2_32.lib")
#pragma comment(lib, "IPHLPAPI.lib")

#define MALLOC(x) HeapAlloc(GetProcessHeap(), 0, (x))
#define FREE(x) HeapFree(GetProcessHeap(), 0, (x))

typedef struct _router
{
	char pGetway[25];
	DWORD dwIF;
}RouterAdapter;

PMIB_IPFORWARDROW pRow[10];		//��ౣ��ʮ��Ĭ��·��
int DRNum=0;	//Ĭ��·�ɵ�����
RouterAdapter ra[10];	//���ʮ�������ۺ�
int raNum=0;


DWORD RouteAdd(char* pIP,char* pGetway,DWORD dwIF)
{
	MIB_IPFORWARDROW IpForwardTable={0};
	ZeroMemory(&IpForwardTable,sizeof(MIB_IPFORWARDROW));
	IpForwardTable.dwForwardDest=inet_addr(pIP);
	IpForwardTable.dwForwardMask=0xFF;
	IpForwardTable.dwForwardNextHop=inet_addr(pGetway);
	IpForwardTable.dwForwardIfIndex=dwIF;
	IpForwardTable.dwForwardProto=MIB_IPPROTO_NETMGMT;
	IpForwardTable.dwForwardMetric1=25;

	DWORD dwRetVal;
	if(&IpForwardTable!=NULL)
	{
		dwRetVal = CreateIpForwardEntry(&IpForwardTable);  
	}
	if (dwRetVal == NO_ERROR)
	{
		printf(".");  
	}
	else if (dwRetVal == ERROR_INVALID_PARAMETER)
	{
		printf("X");
	}
	else
	{
		printf("Error: %d\n", dwRetVal);
	}  
	return dwRetVal;
} 

DWORD RouteDelete()
{
	PMIB_IPFORWARDTABLE pIpForwardTable = NULL;
	DWORD dwSize = 0;
	BOOL bOrder = FALSE;
	DWORD dwStatus = 0;
	unsigned int i;

	dwStatus = GetIpForwardTable(pIpForwardTable, &dwSize, bOrder);
	if (dwStatus == ERROR_INSUFFICIENT_BUFFER) 
	{
		if (!(pIpForwardTable = (PMIB_IPFORWARDTABLE) malloc(dwSize))) 
		{
			printf("Malloc failed. Out of memory.\n");
		}
		dwStatus = GetIpForwardTable(pIpForwardTable, &dwSize, bOrder);
	}

	if (dwStatus != ERROR_SUCCESS) 
	{
		printf("getIpForwardTable failed.\n");
		if (pIpForwardTable)
		{
			free(pIpForwardTable);
		}
	}

	for (i = 0; i < pIpForwardTable->dwNumEntries; i++)
	{
//		printf("Ŀ�꣺%ld\n",pIpForwardTable->table[i].dwForwardDest);
		
		if (pIpForwardTable->table[i].dwForwardDest==10||
			pIpForwardTable->table[i].dwForwardDest==127||
			pIpForwardTable->table[i].dwForwardDest==192||
			pIpForwardTable->table[i].dwForwardDest==169||
			pIpForwardTable->table[i].dwForwardDest==172)
		{
			continue;
		}

		if (pIpForwardTable->table[i].dwForwardDest>0&&pIpForwardTable->table[i].dwForwardDest<=254)
		{
			dwStatus = DeleteIpForwardEntry(&(pIpForwardTable->table[i]));
			if (dwStatus != ERROR_SUCCESS) 
			{
				printf("Could not delete old gateway\n");
			}
			continue;
		}
	}
	if (pIpForwardTable)
	{
		free(pIpForwardTable);
	}
	return dwStatus;
}

DWORD DefaultRouteSave()
{
	PMIB_IPFORWARDTABLE pIpForwardTable = NULL;
	DWORD dwSize = 0;
	BOOL bOrder = FALSE;
	DWORD dwStatus = 0;
	unsigned int i;

	dwStatus = GetIpForwardTable(pIpForwardTable, &dwSize, bOrder);
	if (dwStatus == ERROR_INSUFFICIENT_BUFFER) 
	{
		if (!(pIpForwardTable = (PMIB_IPFORWARDTABLE) malloc(dwSize))) 
		{
			printf("Malloc failed. Out of memory.\n");
		}
		dwStatus = GetIpForwardTable(pIpForwardTable, &dwSize, bOrder);
	}

	if (dwStatus != ERROR_SUCCESS) 
	{
		printf("getIpForwardTable failed.\n");
		if (pIpForwardTable)
		{
			free(pIpForwardTable);
		}
	}

	for (i = 0; i < pIpForwardTable->dwNumEntries; i++)
	{
		if (pIpForwardTable->table[i].dwForwardDest == 0)
		{
			pRow[DRNum] = (PMIB_IPFORWARDROW) malloc(sizeof (MIB_IPFORWARDROW));
			if (!pRow) 
			{
				printf("Malloc failed. Out of memory.\n");
			}
			memcpy(pRow[DRNum], &(pIpForwardTable->table[i]),sizeof (MIB_IPFORWARDROW));
			DRNum++;
		}
	}
	if (pIpForwardTable)
	{
		free(pIpForwardTable);
	}
	printf("����%d��Ĭ��·��\n",DRNum);
	return dwStatus;
}

void DefaultRouteRestore()
{
	DWORD dwStatus;
	int i;
	for (i=0;i<DRNum;i++)
	{
		dwStatus = CreateIpForwardEntry(pRow[i]);
		if (dwStatus == NO_ERROR)
			printf("Gateway Restore successfully\n");
		else if (dwStatus == ERROR_INVALID_PARAMETER)
			printf("Invalid parameter.\n");
		else
			printf("Error: %d\n", dwStatus);
	}
}
int __cdecl main()
{
	SetConsoleTitle(TEXT("���ؾ��⹤�� - By SinSoul")); 
	HANDLE consolehwnd; 
	consolehwnd = GetStdHandle(STD_OUTPUT_HANDLE); 
	SetConsoleTextAttribute(consolehwnd,0xf); 

	printf("----------------------------ע��--------------------------\n");
	printf("�˳�����޸��������·�ɱ�������������������������\n");
	printf("����㲻֪�����������Щ�����ܷ���Internet,��ֱ���˳�����\n");
	printf("��������ʾΪ0.0.0.0,���������������������\n");
	printf("----------------------------------------------------------\n");
	PIP_ADAPTER_INFO pAdapterInfo;
	PIP_ADAPTER_INFO pAdapter = NULL;
	DWORD dwRetVal = 0;
	UINT i;
	char answer;
	ULONG ulOutBufLen = sizeof (IP_ADAPTER_INFO);
	pAdapterInfo = (IP_ADAPTER_INFO *) MALLOC(sizeof (IP_ADAPTER_INFO));
	if (pAdapterInfo == NULL) 
	{
		printf("Error allocating memory needed to call GetAdaptersinfo\n");
		return 1;
	}

	if (GetAdaptersInfo(pAdapterInfo, &ulOutBufLen) == ERROR_BUFFER_OVERFLOW)
	{
		FREE(pAdapterInfo);
		pAdapterInfo = (IP_ADAPTER_INFO *) MALLOC(ulOutBufLen);
		if (pAdapterInfo == NULL) 
		{
			printf("Error allocating memory needed to call GetAdaptersinfo\n");
			return 1;
		}
	}

	if ((dwRetVal = GetAdaptersInfo(pAdapterInfo, &ulOutBufLen)) == NO_ERROR) 
	{
		pAdapter = pAdapterInfo;
		while (pAdapter)
		{
			SetConsoleTextAttribute(consolehwnd,0xa); 

			printf("�����ӿں�:\t%d\n", pAdapter->Index);
			printf("��������:\t%s\n", pAdapter->Description);
			printf("�����ַ:\t");
			for (i = 0; i < pAdapter->AddressLength; i++) 
			{
				if (i == (pAdapter->AddressLength - 1))
				{
					printf("%.2X\n", (int) pAdapter->Address[i]);
				}
				else
				{
					printf("%.2X-", (int) pAdapter->Address[i]);
				}
			}
			printf("IP��ַ:\t\t%s\n",pAdapter->IpAddressList.IpAddress.String);
			printf("��������:\t%s\n", pAdapter->IpAddressList.IpMask.String);
			printf("����:\t\t%s\n\n", pAdapter->GatewayList.IpAddress.String);
			SetConsoleTextAttribute(consolehwnd,0xe); 
			printf("�������������ӵ�Internet��(y/n)��");
			answer=_getch();
			if (answer=='y')
			{
				_putch(answer);
				printf("\n�Ѿ���¼.\n\n");
				ZeroMemory(&ra[raNum],sizeof(RouterAdapter));
				memcpy(ra[raNum].pGetway,pAdapter->GatewayList.IpAddress.String,25);
				ra[raNum].dwIF=pAdapter->Index;
				raNum++;
			}
			else
			{
				_putch(answer);
				printf("\n����,����������.\n\n");
			}
			pAdapter = pAdapter->Next;
		}
	}
	else 
	{
		printf("GetAdaptersInfo failed with error: %d\n", dwRetVal);
	}
	if (pAdapterInfo)
	{
		FREE(pAdapterInfo);
	}
	if(raNum>1)
	{
		char tempip[25];

		//printf("����Ĭ��·��\n");
		//DefaultRouteSave();

		//printf("ɾ��Ĭ��·��\n");
		
		SetConsoleTextAttribute(consolehwnd,0xd); 
		printf("����%d���������и��ؾ��⣬�Ƿ�ִ�в�����(y/n):",raNum);
		answer=_getch();
		if (answer=='y')
		{
			_putch(answer);
			_putch('\n');
			RouteDelete();
			for (int i=0;i<raNum;i++)
			{
				SetConsoleTextAttribute(consolehwnd,0xa); 
				printf("\n���ص�ַ:%s,�ӿ����:%d\n",ra[i].pGetway,ra[i].dwIF);
				for (int j=i;j<254;j+=raNum)
				{
					if (j==10||j==127||j==192||j==169||j==172)
					{
						continue;
					}
					ZeroMemory(&tempip,25);
					sprintf_s(tempip,"%d.0.0.0",j);
					//printf("%d,",j);
					RouteAdd(tempip,ra[i].pGetway,ra[i].dwIF);
				}
				printf("\n");
			}
			SetConsoleTitle(TEXT("��ò�Ҫ�رճ��� - By SinSoul")); 
			SetConsoleTextAttribute(consolehwnd,0xf);
			printf("\n���ؾ����������...\n");
			SetConsoleTextAttribute(consolehwnd,0xc);
			printf("\n�벻Ҫ�رճ��򣬰��س��ɻָ�Ĭ��·�ɣ�ȡ�����ؾ���.\n");
			_getch();
			SetConsoleTitle(TEXT("���ؾ��⹤�� - By SinSoul"));
			SetConsoleTextAttribute(consolehwnd,0xf);
			printf("\n��ʼ�ָ�Ĭ��·��...\n");
			RouteDelete();
//			DefaultRouteRestore();
			printf("���...\n��������˳�����...\n");
			_getch();
		}
		else
		{
			_putch(answer);
			SetConsoleTextAttribute(consolehwnd,0xf);
			printf("\n������������������˳�����.\n");
			_getch();
		}
	}
	else if(raNum<10)
	{
		SetConsoleTextAttribute(consolehwnd,0xc);
		printf("\nֻ��һ�����������ܸ��ؾ��⡣\n\n");
		SetConsoleTextAttribute(consolehwnd,0xd);
		printf("���������ȡ�����ؾ��⣬������y������������˳�����.(y/n):");
		answer=_getch();
		_putch(answer);
		if (answer=='y')
		{
			SetConsoleTextAttribute(consolehwnd,0xf);
			printf("\n��ʼ�ָ�Ĭ��·��...\n");
			RouteDelete();
			printf("���...\n��������˳�����...");
			_getch();
		}
	}
	else
	{
		SetConsoleTextAttribute(consolehwnd,0xf);
		printf("\n����̫�࣬���ܲ��˰�...\n");
	}
	return 0;
}

