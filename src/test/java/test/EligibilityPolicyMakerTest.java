package test;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nucleus.dao.EligibilityPolicyMakerDAO;
import org.nucleus.dto.EligibilityPolicyTempDTO;
import org.nucleus.entity.temporary.EligibilityPolicyTemp;
import org.nucleus.service.EligibilityPolicyCheckerService;
import org.nucleus.service.EligibilityPolicyMakerService;
import org.nucleus.service.EligibilityPolicyMakerServiceImpl;
import org.nucleus.utility.dtomapper.EligibilityPolicyTempDAOMapper;
import org.nucleus.utility.dtomapper.EligibilityPolicyTempDTOMapper;
import org.nucleus.utility.enums.RecordStatus;
import org.springframework.security.authentication.AuthenticationManager;
import static org.junit.Assert.assertEquals;
import org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EligibilityPolicyMakerTest {
    @Mock
    private EligibilityPolicyMakerDAO makerDAO;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private EligibilityPolicyMakerServiceImpl eligibilityPolicyMakerService;
    @Mock
    private EligibilityPolicyTempDTOMapper policyDTOMapper;
    @Mock
    private EligibilityPolicyTempDAOMapper policyDAOMapper;
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private EligibilityPolicyTempDTOMapper eligibilityPolicyDTOMapper;
    @Mock
    private EligibilityPolicyTempDAOMapper eligibilityPolicyDAOMapper;
    @Mock
    private EligibilityPolicyCheckerService eligibilityPolicyCheckerService;
    @Mock
    private SecurityContextHolder securityContextHolder;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void nullCheckForInsert(){
        assertFalse(eligibilityPolicyMakerService.insertEligibilityPolicy(null));
    }
    @Test
    public void simpleCheckForInsert(){
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("maker1");
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        EligibilityPolicyTempDTO eligibilityPolicyTempDTO=new EligibilityPolicyTempDTO();
        when(eligibilityPolicyMakerService.insertEligibilityPolicy(eligibilityPolicyTempDTO)).thenReturn(true);
        boolean result= eligibilityPolicyMakerService.insertEligibilityPolicy(eligibilityPolicyTempDTO);
        assertTrue(result);
    }
    public void nullCheckForUpdate(){

    }
//    @Test
//    void testUpdateEligibilityPolicy_AdminFlagA_InsertEligibilityPolicyCalled() {
//        // Mocking SecurityContextHolder and Authentication
//        SecurityContextHolder.setContext(new SecurityContextImpl());
//        Authentication authentication = mock(Authentication.class);
//        UserDetails userDetails = mock(UserDetails.class);
//        when(authentication.getPrincipal()).thenReturn(userDetails);
//        when(userDetails.getUsername()).thenReturn("maker");
//        when(authentication.getAuthorities()).thenReturn(List.of(new SimpleGrantedAuthority("ROLE_MAKER")));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        EligibilityPolicyTempDTO eligibilityPoliciesDTO = new EligibilityPolicyTempDTO();
//        eligibilityPoliciesDTO.setFlag(RecordStatus.A);
//        when(makerDAO.insertEligibilityPolicy(eligibilityPoliciesDTO)).thenReturn(true);
//        boolean result = eligibilityPolicyMakerService.updateEligibilityPolicy(eligibilityPoliciesDTO);
//
//        // Verifying that insertEligibilityPolicy was called
//        assertTrue(result);
//        verify(makerDAO, times(1)).insertEligibilityPolicy(eligibilityPoliciesDTO);
//    }
//
//    @Test
//    void testUpdateEligibilityPolicy_MakerRole_FlagMR_UpdateEligibilityPolicyCalled() {
//        SecurityContextHolder.setContext(new SecurityContextImpl());
//        Authentication authentication = mock(Authentication.class);
//        UserDetails userDetails = mock(UserDetails.class);
//        when(authentication.getPrincipal()).thenReturn(userDetails);
//        when(userDetails.getUsername()).thenReturn("maker");
//        when(authentication.getAuthorities()).thenReturn(List.of(new SimpleGrantedAuthority("ROLE_MAKER")));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        EligibilityPolicyTempDTO eligibilityPoliciesDTO = new EligibilityPolicyTempDTO();
//        eligibilityPoliciesDTO.setFlag(RecordStatus.MR);
//
//        when(makerDAO.updateEligibilityPolicy(eligibilityPoliciesDTO)).thenReturn(true);
//
//        boolean result = eligibilityPolicyMakerService.updateEligibilityPolicy(eligibilityPoliciesDTO);
//
//        assertTrue(result);
//        verify(makerDAO, times(1)).updateEligibilityPolicy(eligibilityPoliciesDTO);
//    }
}
