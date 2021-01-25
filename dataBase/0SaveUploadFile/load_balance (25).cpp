#include "loadbalancing.h"

#include <QUuid>
#include <QDebug>
#include <QTimer>
#include <QFile>

#define standartused "standartused"
#define commonlyused "commonlyused"
#define rarelyused  "rarelyused"

LoadBalancing::LoadBalancing(QObject *parent)
{
	appset = ApplicationSettings::instance();
	appset->load("loadbalancing.json",QIODevice::ReadWrite);
	p = new QProcess();
	init();
	analyze = NULL;
	timer = new QTimer();
	connect(timer, SIGNAL(timeout()), SLOT(timeout()));
	timer->start(1000);
}

void LoadBalancing::timeout()
{
	//gettingNetworkData();
}

void LoadBalancing::gettingNetworkData()
{
	QString iface = appset->get("pcap.interface").toString();
	QString pcapstate = appset->get("pcap.status").toString();

	if (!analyze)
		if (pcapstate != "stop") {
			analyze = new PcapAnalyze(iface);
		}
	if (analyze != NULL) {
		logFile("Pcap Started");
		QHash <QString, float> ifacedownload;
		ifacedownload = networkStateInfo();
		logFile(QString("pcap out %1 ~ %2 ~ %3")
				.arg(QString::number(ifacedownload.values().at(0)))
				.arg(QString::number(ifacedownload.values().at(1)))
				.arg(QString::number(ifacedownload.values().at(2)))
				);
	}
}

void LoadBalancing::gettingData(QByteArray data)
{
	if(data.isEmpty()) {
		logFile("Getting Data: Data is empty");
		return;
	}
	QStringList flds = QString (data.data()).split(" ");

	foreach (QString tmp, flds) {
		if (tmp.split(".").size() > 3)
			ip = tmp;
		if (tmp.split(":").size() > 5) {
			tmp.replace("\n", "");
			mac = tmp;
		}
	}
	flds = mac.split(":");
	QStringList ls;
	foreach (QString tmp, flds) {
		if (tmp.size() < 2)
			tmp.replace(tmp, QString("0%1").arg(tmp));
		ls << tmp;
	}
	mac = QString("%1:%2:%3:%4:%5:%6")
			.arg(ls.at(0))
			.arg(ls.at(1))
			.arg(ls.at(2))
			.arg(ls.at(3))
			.arg(ls.at(4))
			.arg(ls.at(5));

	logFile(QString("%1 ~ %2").arg(ip).arg(mac));

	QHash <QString, QString> lease;
	lease.insert(ip, mac);

	checkMacAdress(lease);
}

void LoadBalancing::processOutput()
{
	qDebug() << "asdqe";
}

void LoadBalancing::splitIP()
{
	foreach (const QString &ip, iplist) {
		QStringList flds = ip.split(".");
		if (flds.size() > 3) {
			vlanno = flds.at(1);
			personalgrp = flds.at(2);
		}
		qDebug() << vlanno << personalgrp;
	}
}

void LoadBalancing::checkMacAdress(QHash<QString, QString> lease)
{
	QString ruletype = standartused;

	if (ruletype == standartused) {
		QString macfilepath = appset->get("mac.0.file1").toString();
		QString type = appset->get("mac.0.ruletype").toString();
		if (!macfilepath.isEmpty())
			ruletype = checkMACfile(macfilepath, lease, type);
	}
	if (ruletype == standartused) {
		QString macfilepath = appset->get("mac.1.file2").toString();
		QString type = appset->get("mac.1.ruletype").toString();
		if (!macfilepath.isEmpty())
			ruletype = checkMACfile(macfilepath, lease, type);
	}
	if (ruletype == standartused) {
		QString macfilepath = appset->get("mac.2.file2").toString();
		QString type = appset->get("mac.2.ruletype").toString();
		if (!macfilepath.isEmpty())
			ruletype = checkMACfile(macfilepath, lease, type);
	}
	if (ruletype == standartused) {
		QString macfilepath = appset->get("mac.3.file3").toString();
		QString type = appset->get("mac.3.ruletype").toString();
		if (!macfilepath.isEmpty())
			ruletype = checkMACfile(macfilepath, lease, type);
	}
	checkIPruleList(ip, ruletype);
}

QString LoadBalancing::checkMACfile(const QString &macpath, QHash<QString, QString> lease, QString type)
{
	QString ruletype = standartused;
	QFile macFile(macpath);
	if (!macFile.open(QIODevice::ReadOnly | QIODevice::Text))
		return "";

	while (!macFile.atEnd()) {
		QString tmp = macFile.readLine().data();
		tmp.replace("\n","");
		if (tmp.contains("~")) {
			QStringList flds = tmp.split("~");
			tmp = flds.at(0);
			tmp.remove(" ");
		}
		if (tmp == lease.value(ip)) {
			ruletype = type;
			break;
		} else ruletype = standartused;
	}
	logFile(QString("%1 ~ %2").arg(macpath).arg(ruletype));
	macFile.close();
	return ruletype;
}

void LoadBalancing::checkIPruleList(const QString &ip, const QString &table)
{
	QString tmp;
	int err = processRun("ip rule show");
	if (err != 0)
		logFile("Process Error ~ ip rule show");
	else tmp = p->readAllStandardOutput().data();

	QString defIP;
	QString defTable;

	QStringList flds;
	foreach (QString line, tmp.split("\n")) {
		flds << line.split("\t").at(1);
	}
	addRule(ip, table);
	return;
	QString temp;
	int numberoflist = 0;
	int nolist = 0;
	foreach (QString line, flds) {
		temp = line.split(" ").at(1);
		if (temp.split(".").size() > 3)
			defIP = temp;

		temp = line.split(" ").at(3);
		if (temp.size() > 9)
			defTable = temp;

		if (ip != defIP) {
			nolist++;
		}
		if ((ip == defIP) & (table != defTable)) {
			addRule(ip, table);
			for (int i = 0; i < 10; i ++)
				deleteRule(ip, defTable);
		}

		if ((ip == defIP) &  (table == defTable)) {
			numberoflist ++;
			for (int i = 1; i < numberoflist; i++)
				deleteRule(ip, table);
		}
	}
	if (nolist == flds.size())
		addRule(ip, table);
}

void LoadBalancing::checkIPstatus(const QString &ip)
{
	QStringList flds = ip.split(".");
	if (flds.size() > 3) {
		vlanno = flds.at(1);
		personalgrp = flds.at(2);
	}
	if (personalgrp.toInt() <= 99) {
		addRule(ip, rarelyused);
	} else if ((personalgrp.toInt() > 99) & (personalgrp.toInt() <= 199)) {
		addRule(ip, commonlyused);
	} else if ((personalgrp.toInt() > 199) & (personalgrp.toInt() <= 255)) {
		addRule(ip, standartused);
	}
}

void LoadBalancing::init()
{
	tcp = new TcpServer(this);
	if (tcp->listen(QHostAddress::Any, 8978))
		connect(tcp, SIGNAL(newDataAvailable(QByteArray)), this, SLOT(gettingData(QByteArray)));
	else qDebug() << "not Connection";

}

QHash <QString, float> LoadBalancing::networkStateInfo()
{
	QHash <QString, float> ifacedownload;

	QString iface1 = appset->get("pcap.network_state.0.ip").toString();
	QString iface2 = appset->get("pcap.network_state.1.ip").toString();
	QString iface3 = appset->get("pcap.network_state.2.ip").toString();

	ifacedownload.insert(iface1, analyze->getDstIPStats(iface1) / 8); // MBYTE;
	ifacedownload.insert(iface2, analyze->getDstIPStats(iface2) / 8); // MBYTE;
	ifacedownload.insert(iface3, analyze->getDstIPStats(iface3) / 8); // MBYTE;

	return ifacedownload;
}

int LoadBalancing::addRule(const QString &ip, const QString &table)
{
	logFile(QString("Adding table %1 ~ %2").arg(ip).arg(table));

	int err = processRun(QString ("ip rule add from %1 table %2").arg(ip).arg("commonlyused"));
	if (err != 0) {
		logFile(QString (" Process Error ~ ip rule add from %1 table %2").arg(ip).arg("commonlyused"));
		return 0;
	}
	return 0;
}

int LoadBalancing::deleteRule(const QString &ip, const QString &table)
{
	logFile(QString("Deleting table %1 ~ %2").arg(ip).arg(table));

	int err = processRun(QString ("ip rule delete from %1 table %2").arg(ip).arg(table));
	if (err != 0) {
		logFile(QString (" Process Error ~ ip rule delete from %1 table %2").arg(ip).arg(table));
		return 0;
	}
	return 0;
}

int LoadBalancing::deleteTableRule(const QString &table)
{
	QString cmd = QString("ip rule show | grep -i \"%1\" | wc -l").arg(table);
	int err = processRun(cmd);
	if (err != 0)
		logFile(QString("Process error %1").arg(cmd));
	QString numofrules = p->readAllStandardOutput().data();
	numofrules.remove("\n");

	if (numofrules.toInt() > 0) {
		for (int i = 0 ; i < numofrules.toInt(); i++)
			err = processRun(QString ("ip rule delete table %1").arg(table));
		if (err != 0)
			logFile(QString("Process error %1").arg(table));
		logFile(QString("Deleting rule from table ~ %1").arg(table));
	}
	return 0;
}

void LoadBalancing::addRoute(const QString &iface, const QString &ip, const QString &table)
{
	Q_UNUSED(iface)
	Q_UNUSED(ip)
	Q_UNUSED(table)
}

void LoadBalancing::deleteRoute(const QString &iface, const QString &ip, const QString &table)
{
	Q_UNUSED(iface)
	Q_UNUSED(ip)
	Q_UNUSED(table)
}

void LoadBalancing::logFile(const QString &logdata)
{
	QString logpath = appset->get("pcap.log_path").toString();
	QFile log(logpath);
	if (!log.open(QIODevice::ReadWrite | QIODevice::Append))
		return;

	QTextStream out(&log);
	out << logdata << "\n";
	log.close();
}

QStringList LoadBalancing::iptablesParsing(const QString &cmd)
{
	QStringList flds = cmd.split(",");
	return flds;
}

int LoadBalancing::iptablesRun(const QStringList &cmd)
{
	Q_UNUSED(cmd)
	return 0;
}

int LoadBalancing::processRun(const QString &cmd)
{
	if (!cmd.contains("|")) {
		p->start(cmd);
	} else {
		QString tmpscr = QString("/tmp/btt_process_%1.sh").arg(QUuid::createUuid().toString().split("-").first().remove("{"));
		QFile f(tmpscr);
		if (!f.open(QIODevice::WriteOnly | QIODevice::Text)) {
			logFile(QString("error writing test script '%1'").arg(tmpscr));
			return -2;
		}
		f.write("#!/bin/bash\n\n");
		f.write(cmd.toUtf8());
		f.write("\n");
		f.close();
		QProcess::execute(QString("chmod +x %1").arg(tmpscr));
		p->start(tmpscr);
	}
	if (!p->waitForStarted())
		return -1;
	p->waitForFinished(2000);
	return 0;
}
