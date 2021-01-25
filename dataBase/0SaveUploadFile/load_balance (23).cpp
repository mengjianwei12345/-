#include "LoadBalancer.hxx"

#include <sstream>
#include <iostream>

namespace test
{

LoadBalancer::LoadBalancer(int maxAllowedProviders,
                                       int maxRequestsPerProvider,
                                       InvocationMethodType method )
: _maxAllowedProviders ( maxAllowedProviders )
, _maxRequestsPerProvider ( maxRequestsPerProvider )
, _invocationMethod(method)
, _bRoundRobinInit(false)
, _runCheckId(0)
{
    srand (time(NULL));
}

void LoadBalancer::RegisterNewProvider ( std::shared_ptr<test::Provider> provider )
{
    auto it = _mProvider.find ( provider->GetId() );
    if ( it not_eq _mProvider.end() )
    {
        std::ostringstream s;
        s << "Error: provider id " << provider->Get() << " is already registered" << std::endl;
        throw s.str();
     }
     else
     {
         if ( _mProvider.size() >= _maxAllowedProviders )
         {
             std::ostringstream s;
             s << "Error: maximum number of providers reached (" << _maxAllowedProviders << ")" << std::endl;
             throw s.str();
         }
         else
         {
               ProviderStructure st = { provider, true , 0, 0 };
             _mProvider [provider->GetId()] = st;
         }
     }
}

void LoadBalancer::SetInvocationMethod ( InvocationMethod method )
{
    _invocationMethod = method;
}

std::string LoadBalancer::Get()
{
    if ( _mProvider.empty() )
    {
        std::ostringstream s;
        s << "Error: load balancer is empty" << std::endl;
        throw s.str();
    }

    if ( GetNbIncludedProviders() == 0 )
    {
        std::ostringstream s;
        s << "Error: all providers are disconnected" << std::endl;
        throw s.str();
    }

    if ( GetNbRunningRequests() >= GetNbIncludedProviders() * _maxRequestsPerProvider )
    {
        std::ostringstream s;
        s << "Error: cluster capacity limit reached" << std::endl;
        throw s.str();
    }

    bool bFound = false;
    MapProviderT::iterator itFound;

    while ( not bFound )
    {
            if ( _invocationMethod == RANDOM )
            {
                  auto random = rand() % ( _mProvider.size());
                  auto it = _mProvider.begin();
                  std::advance(it, random);
                  if ( IsProviderValidForInvocation ( it->second.provider->Get() ) )
                  {
                        bFound = true;
                        itFound = it;
                  }
            }
            else
            {
                  // ROUND_ROBIN invocation
                  if ( _bRoundRobinInit == false )
                  {
                        _itRoundRobin = _mProvider.begin();
                        _bRoundRobinInit = true;
                  }
                  else
                  {
                        ++_itRoundRobin;
                        if ( _itRoundRobin == _mProvider.end() )
                        {
                              _itRoundRobin = _mProvider.begin();
                        }
                  }
                  if ( IsProviderValidForInvocation ( _itRoundRobin->second.provider->Get() ) )
                  {
                        bFound = true;
                        itFound = _itRoundRobin;
                  }
            }
    }

    itFound->second.nbRequests++;
    return itFound->second.provider->Get();
}

void LoadBalancer::SwitchInclusion ( std::string id , bool newStateValue )
{
      // no error handling here for the sake of simplicity
      _mProvider[std::stoi( id )].state = newStateValue;
}

bool LoadBalancer::IsProviderIncluded ( std::string id )
{
      // no error handling here for the sake of simplicity
      return _mProvider[std::stoi( id )].state;
}

bool LoadBalancer::IsProviderValidForInvocation ( std::string id )
{
      auto & entry = _mProvider[std::stoi( id )];
      return entry.state and entry.nbRequests < _maxRequestsPerProvider;
}

int LoadBalancer::GetNbIncludedProviders()
{
      int i = 0;

      for ( auto & entry : _mProvider )
      {
            if ( IsProviderIncluded ( entry.second.provider->Get() ) )
            {
                  i++;
            }
      }
      return i;
}

int LoadBalancer::GetNbRunningRequests()
{
      int i = 0;

      for ( auto & entry : _mProvider )
      {
            if ( IsProviderIncluded ( entry.second.provider->Get() ) )
            {
                  i += entry.second.nbRequests;
            }
      }
      return i;
}

void LoadBalancer::Check ( )
{
      _runCheckId++;

      for ( auto & entry : _mProvider )
      {
            if ( not entry.second.provider->Check() )
            {
                  // provider is NOT alive
                     SwitchInclusion ( entry.second.provider->Get() , false );
                  entry.second.runCheckId = 0;
            }
            else
            {
                  // provider is alive
                  if ( not IsProviderIncluded ( entry.second.provider->Get() ) )
                  {
                        if ( _runCheckId == entry.second.runCheckId + 1 )
                        {
                              SwitchInclusion ( entry.second.provider->Get() , true );
                              entry.second.runCheckId = 0;

                        }
                  }
                  entry.second.runCheckId = _runCheckId;
            }
      }
}

}
