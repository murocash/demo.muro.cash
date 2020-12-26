package cash.muro.demo.repos;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cash.muro.demo.model.Peak;

@Repository
public interface PeakRepository extends PagingAndSortingRepository<Peak, Integer> {
	
	List<Peak> findAllByOrderByElevationDescDistanceAscNameAsc(Pageable pageable);

}
