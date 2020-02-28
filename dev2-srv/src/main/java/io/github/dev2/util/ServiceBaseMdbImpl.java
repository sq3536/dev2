package io.github.dev2.util;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ServiceBaseMdbImpl <M extends MongoRepository<T,ID>, T> implements IService<T>
{

	private M baseRepository;

	public M getBaseRepository()
	{
		return baseRepository;
	}

	@Override
	public boolean save(T entity)
	{
		getBaseRepository().save(entity);
		return true;
	}

	@Override
	public boolean saveBatch(Collection<T> entityList)
	{
		return this.saveBatch(entityList,DEFAULT_BATCH_SIZE);
	}

	@Override
	public boolean saveBatch(Collection<T> entityList, int batchSize)
	{
		this.getBaseRepository().saveAll(entityList);
		return true;
	}

	@Override
	public boolean saveOrUpdateBatch(Collection<T> entityList)
	{
		return this.saveOrUpdateBatch(entityList,DEFAULT_BATCH_SIZE);
	}

	@Override
	public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize)
	{
		this.getBaseRepository().saveAll(entityList);
		return true;
	}

	@Override
	public boolean removeById(Serializable id)
	{
		this.getBaseRepository().deleteById(id);
		return false;
	}

	@Override
	public boolean removeByMap(Map<String, Object> columnMap)
	{
		return false;
	}

	@Override
	public boolean remove(Wrapper<T> queryWrapper)
	{
		return false;
	}

	@Override
	public boolean removeByIds(Collection<? extends Serializable> idList)
	{
		return false;
	}

	@Override
	public boolean updateById(T entity)
	{
		return false;
	}

	@Override
	public boolean update(Wrapper<T> updateWrapper)
	{
		return false;
	}

	@Override
	public boolean update(T entity, Wrapper<T> updateWrapper)
	{
		return false;
	}

	@Override
	public boolean updateBatchById(Collection<T> entityList)
	{
		return this.updateBatchById(entityList,DEFAULT_BATCH_SIZE);
	}

	@Override
	public boolean updateBatchById(Collection<T> entityList, int batchSize)
	{
		this.getBaseRepository().saveAll(entityList);
		return true;
	}

	@Override
	public boolean saveOrUpdate(T entity)
	{
		this.getBaseRepository().save(entity);
		return true;
	}

	@Override
	public T getById(Serializable id)
	{
		return this.getBaseRepository().findById(id).get();
	}

	@Override
	public List<T> listByIds(Collection<? extends Serializable> idList)
	{
		this.getBaseRepository().findAllById(idList);
		return null;
	}

	@Override
	public List<T> listByMap(Map<String, Object> columnMap)
	{
		return null;
	}

	@Override
	public T getOne(Wrapper<T> queryWrapper)
	{
		queryWrapper.
		return null;
	}

	@Override
	public T getOne(Wrapper<T> queryWrapper, boolean throwEx)
	{
		return null;
	}

	@Override
	public Map<String, Object> getMap(Wrapper<T> queryWrapper)
	{
		return null;
	}

	@Override
	public <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper)
	{
		return null;
	}

	@Override
	public int count()
	{
		return (int)this.getBaseRepository().count();
	}

	@Override
	public int count(Wrapper<T> queryWrapper)
	{
		return 0;
	}

	@Override
	public List<T> list(Wrapper<T> queryWrapper)
	{
		return null;
	}

	@Override
	public List<T> list()
	{
		return null;
	}

	@Override
	public <E extends IPage<T>> E page(E page, Wrapper<T> queryWrapper)
	{
		this.getBaseRepository().findAll(Example)
		return null;
	}

	@Override
	public <E extends IPage<T>> E page(E page)
	{
		return null;
	}

	@Override
	public List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper)
	{
		return null;
	}

	@Override
	public List<Map<String, Object>> listMaps()
	{
		return null;
	}

	@Override
	public List<Object> listObjs()
	{
		return null;
	}

	@Override
	public <V> List<V> listObjs(Function<? super Object, V> mapper)
	{
		return null;
	}

	@Override
	public List<Object> listObjs(Wrapper<T> queryWrapper)
	{
		return null;
	}

	@Override
	public <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper)
	{
		return null;
	}

	@Override
	public <E extends IPage<Map<String, Object>>> E pageMaps(E page, Wrapper<T> queryWrapper)
	{
		return null;
	}

	@Override
	public <E extends IPage<Map<String, Object>>> E pageMaps(E page)
	{
		return null;
	}

	@Override
	public BaseMapper<T> getBaseMapper()
	{
		return null;
	}

	@Override
	public QueryChainWrapper<T> query()
	{
		return null;
	}

	@Override
	public LambdaQueryChainWrapper<T> lambdaQuery()
	{
		return null;
	}

	@Override
	public UpdateChainWrapper<T> update()
	{
		return null;
	}

	@Override
	public LambdaUpdateChainWrapper<T> lambdaUpdate()
	{
		return null;
	}

	@Override
	public boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper)
	{
		this.getBaseRepository().
		return false;
	}
}
